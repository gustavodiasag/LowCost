let loadMore = document.querySelector('#carrega-mais');
let negocie = document.querySelector('#negociar');
let loadMore2 = document.querySelector('#carrega-mais2');
let empresas = document.querySelector('#empresas');
let loadMore3 = document.querySelector('#carrega-noti');
let noti = document.querySelector('#noticias');


loadMore.onclick = () => {
  const li = document.createElement('ul');
  li.innerHTML = '<ul><li class="list-group-item lista-item"><div class="row"><img src="images/perfil.png" class="perfil2"> <p class="p-perfil">Perfil de usuário</p><button type="button" class="btn botao-padrao alinha-2" data-toggle="modal"data-target=".modal-perfil">Entre em contato</button></div></li><li class="list-group-item lista-item"><div class="row"><img src="images/perfil.png" class="perfil2"> <p class="p-perfil">Perfil de usuário</p><button type="button" class="btn botao-padrao alinha-2" data-toggle="modal"data-target=".modal-perfil">Entre em contato</button></div></li><li class="list-group-item lista-item"><div class="row"><img src="images/perfil.png" class="perfil2"> <p class="p-perfil">Perfil de usuário</p><button type="button" class="btn botao-padrao alinha-2" data-toggle="modal"data-target=".modal-perfil">Entre em contato</button></div></li></ul>';
  negocie.appendChild(li);
}

loadMore2.onclick = () => {
  for (let i = 0; i < 3; i++) {
    const divi = document.createElement('div');
    divi.innerHTML = '<div class="card"><h5 class="card-header">Empresa</h5><div class="card-body"><p class="card-text">With supporting text below as a natural lead-in to additional content.</p><button type="button" class="btn botao-padrao" data-toggle="modal" data-target=".modal-empresa">Entre em contato</button></div></div>';
    empresas.appendChild(divi);
  }
}

loadMore3.onclick = () => {
  const row = document.createElement('div');
  row.innerHTML = '<div class="row"> <div class="col-12 col-sm-12 col-md-6 col-lg-4"><div class="card"><div class="card-body"><h5 class="card-title">Notícia</h5><p class="card-text">Some quick example text to build on the card title and make up the bulk of the cards content.</p><a href="saibamais.html" class="btn botao-padrao">Saiba mais</a></div></div></div><div class="col-12 col-sm-12 col-md-6 col-lg-4"><div class="card"><div class="card-body"><h5 class="card-title">Notícia</h5><p class="card-text">Some quick example text to build on the card title and make up the bulk of the cards content.</p><a href="saibamais.html" class="btn botao-padrao">Saiba mais</a></div></div></div><div class="col-12 col-sm-12 col-md-6 col-lg-4"><div class="card"><div class="card-body"><h5 class="card-title">Notícia</h5><p class="card-text">Some quick example text to build on the card title and make up the bulk of the cards content.</p><a href="saibamais.html" class="btn botao-padrao">Saiba mais</a></div></div></div> </div>';
  noti.appendChild(row);
}