1.1: Hoje, ciNatan.aplicarRendimento(-0.10) não trava e não avisa nada — o saldo continua igual e nenhuma linha aparece no extrato. Por que isso é pior do que lançar uma exceção?



**Por que o usuário da conta não tem/terá ideia se foi ou não aplicado o rendimento, se deu erro ou não. Por isso é importante tratar a excessão.**



1.2: Por que faz mais sentido validar dentro de aplicarRendimento() do que dentro de Conta.depositar(), que já existe e já é chamado por todo depósito do projeto?



**Por que a ação depositar não finalizaria e quem chamou o "aplicar rendimento" não saberia do erro. A ideia é que a exceção/erro seja lançad@ para a responsabilidade do método correspondente.**



1.3: Se aplicarRendimento() capturasse a própria exceção com um try/catch dentro dele mesmo, o que mudaria para quem chama o método? Por que isso anularia o propósito do exercício?



**Por que quem chamou o método não escolheria o que fazer com a excessão.**

