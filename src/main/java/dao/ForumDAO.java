package dao;

import model.Forum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ForumDAO extends DAO {
		
	public ForumDAO() { super(); connect(); }

	public void finalize() { close(); }

	public boolean insert(Forum forum) {
		
		if (this.getId(forum.getTitle()) != 0) return true;

		else {
			
			boolean status = false;
			
			try {
				
				String sql = "INSERT INTO forum (comments, title, user_id_fk) VALUES ("
						+ forum.getComments() + ", '"
						+ forum.getTitle() + "', "
						+ forum.getUserId() + ");";
				
				PreparedStatement st = connection.prepareStatement(sql);
				
				st.executeUpdate();
				st.close();
				
				status = true;
				
			} catch (SQLException e) { throw new RuntimeException(e); }
			
			return status;
		}

	}

	public int getId(String title) {

		int id = 0;

		try {

			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String sql = "SELECT * FROM forum WHERE title = '" + title + "';";

			ResultSet rs = st.executeQuery(sql);

			if (rs.next())
				id = rs.getInt("id");

			st.close();

		} catch (Exception e) { System.err.println(e.getMessage()); }

		return id;
	}
	
	public String getTitle(int id) {
		
		String title = "";
		
		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String sql = "SELECT title FROM forum WHERE id = " + id;
			
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next())
				title = rs.getString("title");
			
			st.close();
			
		} catch (Exception e) { System.err.println(e.getMessage()); }
		
		return title;
	}
	
	public List<List<String>> getAll(String orderBy) {
		
		List<List<String>> forums = new ArrayList<List<String>>();
		
		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "SELECT title, comments, public.forum.id, login FROM public.forum "
						 + "JOIN public.user ON public.forum.user_id_fk = public.user.id "
						 + ((orderBy.isEmpty()) ? "" : "ORDER BY " + orderBy + " DESC");
			
			ResultSet rs = st.executeQuery(sql);
			
	        while(rs.next()) {
	        	
	        	List<String> data = new ArrayList<String>();
	        	
	        	data.add(rs.getString("title"));
	        	data.add(String.valueOf(rs.getInt("comments")));
	        	data.add(String.valueOf(rs.getInt("id")));
	        	data.add(rs.getString("login"));
	        	
	        	forums.add(data);
	        }
	        
	        st.close();
	        
		} catch (Exception e) { System.err.println(e.getMessage()); }
		
		return forums;
	}
	
	public List<List<String>> getComments(int forumId) {
		
		List<List<String>> comments = new ArrayList<List<String>>();
		
		try {
			
			Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
				String sql = "SELECT content, login FROM public.forum "
							 + "JOIN public.comment ON public.forum.id = public.comment.forum_id_fk "
							 + "JOIN public.user ON public.comment.user_id_fk = public.user.id "
							 + "WHERE public.forum.id =" + forumId
							 + "ORDER BY submission";
			
			ResultSet rs = st.executeQuery(sql);
			
	        while(rs.next()) {
	        	
	        	List<String> data = new ArrayList<String>();
	        	
	        	data.add(rs.getString("content"));
	        	data.add(rs.getString("login"));
	        	
	        	comments.add(data);
	        }
	        
	        st.close();
	        
		} catch (Exception e) { System.err.println(e.getMessage()); }
		
		return comments;
	}

	public boolean update(Forum forum) {

		boolean status = false;

		try {

			String sql = "UPDATE forum SET comments = " + forum.getComments()
						 + ", title = '" + forum.getTitle()
						 + "', user_id_fk = " + forum.getUserId()
						 + " WHERE id = " + forum.getId();

			PreparedStatement st = connection.prepareStatement(sql);

			st.executeUpdate();
			st.close();

			status = true;

		} catch (SQLException e) { throw new RuntimeException(e); }

		return status;
	}
	
	public boolean updateComments(int forumId) {
		
		boolean status = false;
		
		try {
			
			String sql = "UPDATE public.forum SET comments = comments + 1 "
						 + "WHERE id = " + forumId;
			
			PreparedStatement st = connection.prepareStatement(sql);
			
			st.executeUpdate();
			st.close();
			
			status = true;
			
		} catch (SQLException e) { throw new RuntimeException(e); }
		
		return status;
	}

	public boolean delete(int forumId) {

		boolean status = false;

		try {

			Statement st = connection.createStatement();

			st.executeUpdate("DELETE FROM forum WHERE id = "  + forumId);
			st.close();

			status = true;

		} catch (SQLException e) { throw new RuntimeException(e); }

		return status;
	}
	
	public boolean deleteFromUser(int userId) {
		
		boolean status = false;
		
		try {
			
			Statement st = connection.createStatement();
			
			st.executeUpdate("DELETE FROM forum WHERE user_id_fk = " + userId);
			st.close();
			
			status = true;
			
		} catch(SQLException e) { throw new RuntimeException(e); }
		
		return status;
	}
}