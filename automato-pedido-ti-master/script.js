// Variáveis

var pedido, estadoPedido, infoPedido;
estadoPedido = document.getElementById("estado_pedido");
infoPedido = document.getElementById("info_pedido");
meuGif = document.getElementById("img_teste");
solicitarDev = document.querySelector(".solicitar_devolucao");
solicitarDev.style.display = "none";


pedidoGif = "https://media2.giphy.com/media/j3x5hjUoXIesM/200.webp?cid=790b761121096771a895a67f27dab0bb6d0706c7a9b4c2fc&rid=200.webp"; 
realizarGif = "https://media1.giphy.com/media/JpG2A9P3dPHXaTYrwu/200w.webp?cid=790b7611d4eff1701c1822fd7a22c35fbe8326fd52202885&rid=200w.webp";
verificarPagamento = "https://media3.giphy.com/media/67ThRZlYBvibtdF9JH/200.webp?cid=790b7611fc0f4560edbe9e4928617b8553d53f0a66876e4c&rid=200.webp"
prepararGif = "http://www.gifbay.com/gif/when_my_gf_asks_for_some_money_to_go_shopping-134988/";
entregueGif = "https://media1.giphy.com/media/11sBLVxNs7v6WA/100.webp?cid=790b761199ebfdaff7e3649982b2faf9a01c904e120d22e6&rid=100.webp";
canceladoGif = "https://media3.giphy.com/media/13EjnL7RwHmA2Q/200w.webp?cid=790b76119efbb9c6054e0ee7a8f0a628320d77bc7b02cc05&rid=200w.webp";
invalidoGif = "https://media3.giphy.com/media/LZ4YlxtpHWoIXMFLFC/200w.webp?cid=790b7611c60ddaa8d75582384dbb6fc0ed278b70f46feeae&rid=200w.webp";

infoPedido.style.display = "none";
meuGif.style.display = "none";

// Funções de alerta

function alertDisplaySuccess() {
    infoPedido.classList.remove("alert-danger");
    infoPedido.classList.add("alert-success");
    infoPedido.style.display = "block";
}

function alertDisplayDanger() {
    infoPedido.classList.add("alert-danger");
    infoPedido.classList.remove("alert-success");
    infoPedido.style.display = "block";
}

function alertDisplay() {
    infoPedido.classList.remove("alert-danger");
    infoPedido.classList.remove("alert-success");
    infoPedido.classList.add("alert-primary");
    infoPedido.style.display = "block";
}

// Funções de estado

var Pedido = /** @class */ (function () {
    //No pedido é instanciado todos os estados por ele mesmos
    function Pedido() {
        this.realizarCompraEstado = new RealizarCompra(this);
        this.verificarPagamento = new VerificarPagamento(this);
        this.prepararParaEnvio = new PrepararParaEnvio(this);
        this.entregue = new Entregue(this);
        this.cancelado = new Cancelado(this);
        this.processoDeDevolucao = new ProcessoDeDevolucao(this);
        this.setEstado(this.realizarCompraEstado);
        infoPedido.textContent = "Iniciando nova compra";
    }
    Pedido.prototype.setEstado = function (estado) {
        this.pedidoAtual = estado;
    };
    Pedido.prototype.getEstado = function () {
        return this.pedidoAtual;
    };
    return Pedido;
}());

var RealizarCompra = /** @class */ (function () {
    //FAZENDO UM PEDIDO NOVO E SEUS ESTADOS DE RETORNO
    function RealizarCompra(pedido) {
        this.pedido = pedido;
    }
    RealizarCompra.prototype.pagar = function () {
        infoPedido.textContent = "Pagamento relizado com sucesso, aguarde a verificação!";
        this.pedido.setEstado(this.pedido.verificarPagamento);
        estadoPedido.textContent = "Verificando Pagamento";
        alertDisplaySuccess();
        meuGif.src = realizarGif;
    };
    RealizarCompra.prototype.confirmarPagamento = function () {
        infoPedido.textContent = "O pagamento só pode ser confirmado após a verificação";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    RealizarCompra.prototype.enviar = function () {
        infoPedido.textContent = "O envio só poderá ser feito após as etapas de pagamento";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    RealizarCompra.prototype.cancelar = function () {
        console.log("Pedido sendo cancelado...");
        infoPedido.textContent = "Pedido sendo cancelado...";
        this.pedido.setEstado(this.pedido.cancelado);
        estadoPedido.textContent = "Cancelado";
        alertDisplay();
        meuGif.src = canceladoGif;
    };
    RealizarCompra.prototype.solicitarDevolucao = function () {
        infoPedido.textContent = "Você só poderá solicitar a devolução do produto após entregue.";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    return RealizarCompra;
}());

var VerificarPagamento = /** @class */ (function () {
    //CONFIRMAÇÃO DO PAGAMENTO E SEUS ESTADOS DE RETORNO
    function VerificarPagamento(pedido) {
        this.pedido = pedido;
    }
    VerificarPagamento.prototype.pagar = function () {
        infoPedido.textContent = "O pagamento já foi efetuado, aguarde a verificação...";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    VerificarPagamento.prototype.confirmarPagamento = function () {
        infoPedido.textContent = "Pagamento confirmado e preparando para envio...";
        this.pedido.setEstado(this.pedido.prepararParaEnvio);
        estadoPedido.textContent = "Pagamento Confirmado";
        alertDisplaySuccess();
        meuGif.src = verificarPagamento;
    };
    VerificarPagamento.prototype.enviar = function () {
        infoPedido.textContent = "O pedido só poderá ser enviado após a confirmação do pagamento";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    VerificarPagamento.prototype.cancelar = function () {
        infoPedido.textContent = "Pedido sendo cancelado...";
        this.pedido.setEstado(this.pedido.cancelado);
        estadoPedido.textContent = "Cancelado";
        alertDisplay();
        meuGif.src = canceladoGif;
    };
    VerificarPagamento.prototype.solicitarDevolucao = function () {
        infoPedido.textContent = "Você só poderá solicitar a devolução do produto após entregue.";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    return VerificarPagamento;
}());
var PrepararParaEnvio = /** @class */ (function () {
    //ENVIANDO PRODUTO E SEUS ESTADOS DE RETORNO
    function PrepararParaEnvio(pedido) {
        this.pedido = pedido;
    }
    PrepararParaEnvio.prototype.pagar = function () {
        infoPedido.textContent = "O pagamento já foi efetuado!";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    PrepararParaEnvio.prototype.confirmarPagamento = function () {
        infoPedido.textContent = "O pagamento já foi efetuado!";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    PrepararParaEnvio.prototype.enviar = function () {
        infoPedido.textContent = "Pedido enviado e entregue!";
        this.pedido.setEstado(this.pedido.entregue);
        estadoPedido.textContent = "O pedido foi entregue."
        alertDisplaySuccess();
        meuGif.src = entregueGif;
        solicitarDev.style.display = "block";
    };
    PrepararParaEnvio.prototype.cancelar = function () {
        infoPedido.textContent = "Pedido sendo cancelado...";
        this.pedido.setEstado(this.pedido.cancelado);
        estadoPedido.textContent = "Pedido cancelado";
        alertDisplay();
        meuGif.src = canceladoGif;
    };
    PrepararParaEnvio.prototype.solicitarDevolucao = function () {
        infoPedido.textContent = "Você só poderá solicitar a devolução do produto após entregue.";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    return PrepararParaEnvio;
}());
var ProcessoDeDevolucao = /** @class */ (function () {
        //DEVOLUÇÃO DE PRODUTOS E SEUS ESTADOS DE RETORNO
    function ProcessoDeDevolucao(pedido) {
        this.pedido = pedido;
    }
    ProcessoDeDevolucao.prototype.pagar = function () {
        infoPedido.textContent = "O pagamento já foi efetuado, porém o pedido foi devolvido!";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    ProcessoDeDevolucao.prototype.confirmarPagamento = function () {
        infoPedido.textContent = "Impossível confirmar o pagamento de um pedido que já foi devolvido!!";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    ProcessoDeDevolucao.prototype.enviar = function () {
        infoPedido.textContent = "Impossível enviar um pedido que já foi devolvido!";
        alertDisplayDanger();
        meuGif.src = entregueGif;
    };
    ProcessoDeDevolucao.prototype.cancelar = function () {
        infoPedido.textContent = "Impossível cancelar um pedido que já foi devolvido!";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    ProcessoDeDevolucao.prototype.solicitarDevolucao = function () {
        infoPedido.textContent = "Processando devolução e aguardando confirmação.";
        this.pedido.setEstado(this.pedido.processoDeDevolucao);
        estadoPedido.textContent = "Pedido em processo de devolução";
        alertDisplay();
        meuGif.src = canceladoGif;
    };
    return ProcessoDeDevolucao;
}());
var Entregue = /** @class */ (function () {
        //ENTREGANDO O PRODUTO SE SEUS ESTADOS DE RETORNO
    function Entregue(pedido) {
        this.pedido = pedido;
    }
    Entregue.prototype.pagar = function () {
        infoPedido.textContent = "O pagamento já foi efetuado";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    Entregue.prototype.confirmarPagamento = function () {
        infoPedido.textContent = "O pagamento já foi confirmado";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    Entregue.prototype.enviar = function () {
        infoPedido.textContent = "O pedido já foi entregue";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    Entregue.prototype.cancelar = function () {
        infoPedido.textContent = "O pedido não pode ser cancelado após o envio";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    Entregue.prototype.solicitarDevolucao = function () {
        infoPedido.textContent = "O produto está em processo de devolução";
        estadoPedido.textContent = "Processo de devolução"
        alertDisplay();
        
        meuGif.src = canceladoGif;
        this.pedido.setEstado(this.pedido.processoDeDevolucao);
        setTimeout(function(){ 
            infoPedido.textContent = "O produto foi devolvido e você receberá seu dinheiro em 3 dias.";
            estadoPedido.textContent = "O pedido foi devolvido e a compra cancelada!"
            this.pedido.setEstado(this.pedido.cancelar);
            alertDisplaySuccess();
         }, 3000);

    };
    return Entregue;
}());
var Cancelado = /** @class */ function () {
        //CANCELAMENTO E SEUS ESTADOS DE RETORNO
    function Cancelado(pedido) {
        this.pedido = pedido;
    }
    Cancelado.prototype.pagar = function () {
        infoPedido.textContent = "O pedido foi cancelado, por favor, efetue a compra novamente";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    Cancelado.prototype.confirmarPagamento = function () {
        infoPedido.textContent = "O pedido foi cancelado, por favor, efetue a compra novamente";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    Cancelado.prototype.enviar = function () {
        infoPedido.textContent = "Um pedido cancelado não pode ser enviado";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    Cancelado.prototype.cancelar = function () {
        infoPedido.textContent = "O pedido já foi cancelado!";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    Cancelado.prototype.solicitarDevolucao = function () {
        infoPedido.textContent = "Você só poderá solicitar a devolução do produto após entregue.";
        alertDisplayDanger();
        meuGif.src = invalidoGif;
    };
    return Cancelado;
}();

// 
document.getElementById("estado_pedido").textContent = "Pedido não iniciado";
// Metodos Extras

document.querySelector(".novo_pedido").addEventListener("click", () => {
    pedido = new Pedido();
    solicitarDev.style.display = "none";
    meuGif.src = pedidoGif;
    meuGif.style.display = "block";
    alertDisplay();
    document.getElementById("estado_pedido").textContent = "Novo pedido iniciado";
    console.log("Novo pedido criado");
});

document.querySelector(".pagar_pedido").addEventListener("click", () => {
    pedido.getEstado().pagar();
});

document.querySelector(".confirmar_pagamento").addEventListener("click", () => {
    pedido.getEstado().confirmarPagamento();
});

document.querySelector(".enviar_pedido").addEventListener("click", () => {
    pedido.getEstado().enviar();
});


document.querySelector(".cancelar_pedido").addEventListener("click", () => {
    pedido.getEstado().cancelar();
});

document.querySelector(".solicitar_devolucao").addEventListener("click", () => {
    pedido.getEstado().solicitarDevolucao();
});

