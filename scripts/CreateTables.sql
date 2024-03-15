CREATE TABLE tb_conta (
    NumeroConta char(11) primary key,
    IdCliente varchar(36),
    Saldo decimal(15,2),
    Ativa boolean,
    DataCriacaoConta date
);

CREATE TABLE tb_transferencia (
	CodigoTransferencia binary(16) primary key,
	ValorTransferido decimal(15,2),
    NumeroContaRemetente char(11),
    NumeroContaDestinataria char(11),
	DataHoraTransferencia datetime
);

ALTER TABLE tb_transferencia ADD CONSTRAINT fk_conta_remetente FOREIGN KEY (NumeroContaRemetente) REFERENCES tb_conta(NumeroConta);
ALTER TABLE tb_transferencia ADD CONSTRAINT fk_conta_destinataria FOREIGN KEY (NumeroContaDestinataria) REFERENCES tb_conta(NumeroConta);


