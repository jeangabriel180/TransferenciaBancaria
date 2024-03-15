INSERT INTO `db`.`tb_conta`
(`NumeroConta`,`IdCliente`,`Saldo`,`Ativa`,`DataCriacaoConta`)
VALUES('56984359874',UUID_TO_BIN('3457e1f2-31fc-4401-a89e-abe44f8b7c22'),300.0,true,'2024-02-18');

INSERT INTO `db`.`tb_conta`
(`NumeroConta`,`IdCliente`,`Saldo`,`Ativa`,`DataCriacaoConta`)
VALUES('00360021003',UUID_TO_BIN('3457e1f2-31fc-4401-a89e-abe44f8b7c44'),800.0,true,'2024-02-18');

INSERT INTO `db`.`tb_transferencia`
(`CodigoTransferencia`,
`ValorTransferido`,
`NumeroContaRemetente`,
`NumeroContaDestinataria`,
`DataHoraTransferencia`)
VALUES (
'c257dc33-50a8-4049-9d75-f739f00c2631',
100.00,
'56984359874',
'99876142364',
'2024-02-18 19:30:35'
);
