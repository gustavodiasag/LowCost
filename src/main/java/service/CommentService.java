package service;

import dao.*;
import model.Comment;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;

import com.azure.ai.textanalytics.*;
import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.ai.textanalytics.models.SentimentConfidenceScores;
import com.azure.core.credential.AzureKeyCredential;

public class CommentService {
	
	private TextAnalyticsClient client = new TextAnalyticsClientBuilder()
            .credential(new AzureKeyCredential("ca8de3c4ea004787a0ed003953975170"))
            .endpoint("https://textrecognitionjava.cognitiveservices.azure.com/")
            .buildClient();
	
	private CommentDAO commentDao = new CommentDAO();
	private UserDAO userDao = new UserDAO();
	
	public CommentService() {}
	
	public boolean insertService(Request request, Response response, int companyId, int serviceId) {
		
		String content = request.queryParams("comment");
		LocalDateTime submission = LocalDateTime.now();
		int userId = userDao.getId((String)request.session().raw().getAttribute("login"));
		
		final DocumentSentiment documentSentiment = client.analyzeSentiment(content);
    	
        SentimentConfidenceScores scores = documentSentiment.getConfidenceScores();
		
		Comment comment = new Comment(content, submission, serviceId, 0, userId,
						 			 ((float)scores.getPositive() - (float)scores.getNegative()/(float)content.length()),
						 			 companyId);
		
		return commentDao.insertCommentService(comment);
	}
	
	public boolean insertForum(Request request, Response response) {
		
		String content = request.queryParams("comment");
		LocalDateTime submission = LocalDateTime.now();
		int userId = userDao.getId((String)request.session().raw().getAttribute("login"));
		int forumId = Integer.parseInt(request.params(":id"));
		
		Comment comment = new Comment(content, submission, 0, forumId, userId, 0, 0);
		
		return commentDao.insertCommentForum(comment);
	}

	public boolean deleteCommentService(Request request, Response response) {
		
		int userId = userDao.getId((String)request.session().raw().getAttribute("login"));
		int serviceId = Integer.parseInt(request.params(":serviceId"));
		int companyId = Integer.parseInt(request.params(":companyId"));
		
		return commentDao.deleteCommentService(userId, serviceId, companyId);
	}
	
	public boolean deleteCommentForum(Request request, Response response) {
		
		int forumId = Integer.parseInt(request.params(":id"));
		
		return commentDao.deleteCommentForum(forumId);
	}
	
	public boolean deleteFromUser(Request request, Response response) {
		
		int userId = Integer.parseInt(request.params(":id"));
		
		return commentDao.deleteFromUser(userId);
	}
}
