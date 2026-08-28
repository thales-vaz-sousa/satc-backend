// ============================================================================
//  COLA RÁPIDA -- CONCEITO DE POO  =  PALAVRA-CHAVE DO JAVA
// ============================================================================
//
//  Classe é o molde; 
//  CLASSE          =  class                  -> contas/Conta.java: a "planta baixa"
//  ------------------------------------------------------
//  Objeto é a instância;
//  OBJETO          =  new                    -> new ContaCorrente("Natan", "1234-5")
//  ------------------------------------------------------
//  Abstração e interfaces ajudam a definir modelos e contratos
//  Abstração       =  abstract               -> ContaCorrente, ContaPoupanca
//  ------------------------------------------------------
//  Encapsulamento: proteger atributos com private e acessá-los por getters e setters quandonecessário.
//  ENCAPSULAMENTO  =  private                -> private double saldo (e nenhum setSaldo)
//  ------------------------------------------------------
//  Herança: uma classe filha herda atributos e métodos da classe mãe usando extends.
//  HERANÇA         =  extends  +  super      -> ContaCorrente extends Conta
//  ------------------------------------------------------
//  INTERFACE       =  interface + implements -> Conta implements Tributavel
//  ------------------------------------------------------
//  Polimorfismo permite tratar objetos de subclasses por uma referência da superclasse.
//  POLIMORFISMO    =  @Override              -> conta.sacar(100): a MESMA linha roda
//                                               código diferente conforme o objeto
//  ------------------------------------------------------
//  Outros termos que aparecem no código:
//  ------------------------------------------------------
//  Atributos representam características; 
//  ATRIBUTO        =  private                -> private String titular
//  ------------------------------------------------------
//  métodos representam comportamentos.
//  MÉTODO          =  public                 -> public void sacar(double valor)
//  ------------------------------------------------------
//  CONSTRUTOR      =  método com o nome da classe e SEM tipo de retorno
//  ------------------------------------------------------
//  SOBRECARGA      =  mesmo nome, parâmetros diferentes  -> depositar(double)
//                                                           depositar(double, String)
//  ------------------------------------------------------
//  Sobrescrita ocorre quando a subclasse reimplementa um método herdado.
//  SOBRESCRITA     =  @Override na subclasse             -> ContaCorrente.sacar()
//  ------------------------------------------------------
//  COMPOSIÇÃO      =  atributo que é outro objeto        -> Conta TEM lançamentos
//  ------------------------------------------------------
//  CONSTANTE       =  static final                       -> TAXA_SAQUE = 0.50
//  ------------------------------------------------------
//  PACOTE          =  package  +  import                 -> package contas;
//  ------------------------------------------------------
//  Cuidado para não confundir:
//    HERANÇA  = parentesco  ("ContaCorrente É uma Conta")     -> extends, só 1 mãe
//    INTERFACE= compromisso ("Acao SABE calcular imposto")    -> implements, quantas quiser
//
//  Para achar cada conceito dentro do código:
//    grep -rn ">>> POLIMORFISMO" src/main/java     (troque pelo conceito que quiser)
//    grep -rn ">>> " src/main/java                 (lista as 36 marcações de uma vez)
//
// ============================================================================

// COMO RODAR (a partir da pasta que contém o pom.xml):
//   Linux/Mac:  ./mvnw test && java -cp target/classes app.Main
//   Windows:    mvnw.cmd test && java -cp target/classes app.Main
//
// O "mvnw" é o Maven Wrapper: um script que baixa e usa o Maven certo sozinho.
// Quem clonar o projeto precisa ter só o JDK instalado -- Maven não precisa estar na máquina.
// Acrescente -o para rodar offline depois do primeiro download: ./mvnw -o test
//
// Se o mvnw.cmd der erro de permissão do Windows (comum em máquina de laboratório),
// rode os testes DIRETO PELO INTELLIJ, sem precisar do wrapper:
//   1. File > Open -> escolhe esta pasta (a que tem o pom.xml). O IntelliJ importa
//      o projeto Maven sozinho.
//   2. Botão direito em src/test/java (ou numa classe de teste específica, ex.:
//      ContaCorrenteTest) -> "Run 'Tests in ...'".
//   3. Se reclamar de SDK, ajusta em File > Project Structure > Project.
// O IntelliJ usa o runner de JUnit dele direto -- nem chama o Maven wrapper.
//
// Main fica no pacote "app" (src/main/java/app/). Repare no mapa do projeto:
//
//   src/main/java/
//     ├── tributaveis/   -> Tributavel                        (o contrato)
//     ├── contas/        -> Conta, ContaCorrente, ContaPoupanca (implementa o contrato)
//     ├── investimentos/ -> Acao                              (implementa o contrato)
//     └── app/           -> Main                              (usa todo mundo)
//
// Dependências: contas -> tributaveis <- investimentos, e app -> todos.
// Nenhum pacote de domínio conhece o outro; os dois só conhecem a interface.
//
// Antes, TODAS as classes ficavam soltas em src/main/java/ sem declarar pacote: caíam no
// "pacote default" (unnamed package), enxergavam umas às outras sem import e nenhum projeto
// real faz isso. Agora cada classe tem um endereço completo: contas.ContaCorrente,
// investimentos.Acao, app.Main.
//
// Declara o pacote; a pasta app/ precisa existir e ter exatamente este nome.
package app;

// Como Main está em app, TUDO que vem de fora precisa de import -- inclusive as classes
// do próprio projeto. É a mesma regra do ArrayList, que vem do pacote java.util.
//
// ArrayList: lista que cresce sozinha; guarda a lista polimórfica lá embaixo.
import java.util.ArrayList;
import java.util.Locale;

import contas.Conta;
import contas.ContaCorrente;
import contas.ContaEstrangeira;
import contas.ContaInvestimento;
import contas.ContaPoupanca;
import investimentos.Acao;
import tributaveis.Tributavel;

// Classe principal, ponto de entrada do programa.
// O nome da classe tem que ser igual ao nome do arquivo (Main.java) -- regra do Java.
public class Main {

    // Locale do Brasil: faz o valor sair como 1.234,56 em vez de 1,234.56.
    // Locale é uma classe da API Java que representa uma região e idioma.
    // private: Significa que a variável BR só pode ser acessada dentro da própria classe. Outras classes não conseguem acessar BR diretamente.
    // static: A variável pertence à classe, e não aos objetos.
    // Final: Significa que a referencia não pode ser alterada.
    private static final Locale BR = Locale.of("pt", "BR");

    // Mesma largura usada nos extratos, para tudo alinhar na tela.
    private static final int LARGURA = 45;

    // main(): método que a JVM chama para iniciar a execução.
    // static = roda sem precisar de "new Main()".
    public static void main() {

        // >>> OBJETO: "ContaCorrente" é a CLASSE (a forma); "ccNatan" é o OBJETO (o exemplar).
        // Uma classe só existe uma vez no projeto; objetos você cria quantos quiser, e cada um
        // tem seus próprios valores. É a diferença entre a receita e o bolo.
        //
        // Os dados vão no CONSTRUTOR: a conta já nasce com dono e número, sem passar por
        // um estado inválido. Compare com a versão antiga, que era "new" mais três setters.
        ContaCorrente ccNatan = new ContaCorrente("Natan", "1234-5");

        // Saldo: 1000.00 -- e já entra a primeira linha do extrato.
        ccNatan.depositar(1000);
        ccNatan.depositar(999999);

        // Cria uma ContaPoupanca (também subclasse de contas.Conta).
        // Outro objeto, com memória separada da conta acima.
        ContaPoupanca cpWesley = new ContaPoupanca("Wesley", "6789-0");

        // Saldo: 1000.00 -- mesmo método herdado, outro objeto.
        cpWesley.depositar(1000);

        // >>> HERANÇA + POLIMORFISMO: nenhuma das duas classes escreveu "sacar" do zero.
        // Uma herdou e a outra sobrescreveu -- e a chamada aqui é idêntica nas duas linhas.
        // Mesmo código, resultados diferentes: R$ 0,50 de taxa numa, nada na outra.
        //
        // Saldo: 899.50 -- cai na versão de ContaCorrente (100 + 0.50 de taxa).
        ccNatan.sacar(100);

        // Saldo: 900.00 -- cai na versão de Conta (sem taxa).
        cpWesley.sacar(100);

        // >>> HERANÇA (o outro caminho): método que só a poupança tem. A subclasse pode
        // ADICIONAR comportamento, não só sobrescrever. Saldo: 904.50 (0,5% sobre 900.00).
        // A ccNatan nem enxerga este método -- herança não é troca simétrica.
        cpWesley.aplicarRendimento();

        try {

            cpWesley.sacar(999999);

            System.out.println(">> Saque de R$ 999.999,00 na conta " + cpWesley.getNumero() + " AUTORIZADO");

        } catch (IllegalArgumentException e) {

            System.out.println(">> Saque de R$ 999.999,00 na conta " + cpWesley.getNumero()
                    + " RECUSADO (" + e.getMessage() + ")");

        } finally {

            System.out.println(">> Fim da tentativa de saque.");

        }

        System.out.println();

        Conta[] listaContas = {ccNatan, cpWesley};

        System.out.println("-".repeat(LARGURA));

        System.out.println("MOVIMENTAÇÃO: saque de R$ 50,00 em cada conta");

        System.out.println("-".repeat(LARGURA));

        for (int i = 0; i < listaContas.length; i++) {

            Conta conta = listaContas[i];

            conta.sacar(50);

            System.out.println(String.format(BR, "%-28.28s %,16.2f", conta.getTitular(), conta.getSaldo()));

        }

        System.out.println();

        ContaCorrente ccOrigem = new ContaCorrente("Natan", "1234-5");

        ccOrigem.depositar(1000);

        ccOrigem.sacar(100);

        ContaEstrangeira ceNatan = new ContaEstrangeira("Natan", "9999-9");

        ccOrigem.transferir(ceNatan, 500);

        System.out.println("-".repeat(LARGURA));
        System.out.println("TRANSFERÊNCIA: R$ 500,00 da corrente para o dólar");
        System.out.println("-".repeat(LARGURA));

        System.out.println("Autorizada?         true");

        System.out.println(String.format(BR, "%-28.28s %,16.2f", "Saldo da corrente (R$)", ccOrigem.getSaldo()));

        System.out.println(String.format(BR, "%-28.28s %,16.2f", "Saldo da estrangeira (US$)", ceNatan.getSaldo()));

        System.out.println(String.format(BR, "%-28.28s %,16.2f", "Convertido de volta (R$)", ceNatan.getSaldoEmReais()));

        System.out.println("   valor cru, sem %.2f: " + ceNatan.getSaldoEmReais());

        try {

            ccOrigem.transferir(ceNatan, 99999);

            System.out.println("Transferir R$ 99.999? true");

        } catch (IllegalArgumentException e) {

            System.out.println("Transferir R$ 99.999? false (" + e.getMessage() + ")");

        } finally {
            System.out.println("Fim da tentativa de transferência.");
        }

        System.out.println();

        ContaInvestimento ciNatan = new ContaInvestimento("Natan", "7777-7");

        ciNatan.depositar(1000);

        String naoAplicavelRendimentoMensagem = "Não foi possivel aplicar rendimento: ";
        String operacaoFinalizadaMensagem = "Operação de rendimento finalizada.";

        try {
            ciNatan.aplicarRendimento(0.10);
        }
        catch (IllegalArgumentException e) {
            System.out.println(naoAplicavelRendimentoMensagem + e.getMessage());
        }
        finally {
            System.out.println(operacaoFinalizadaMensagem);
        }

        try {
            ciNatan.aplicarRendimento(0);
        }
        catch (IllegalArgumentException e) {
            System.out.println(naoAplicavelRendimentoMensagem + e.getMessage());
        }
        finally {
            System.out.println(operacaoFinalizadaMensagem);
        }

        try {
            ciNatan.aplicarRendimento(-0.10);
        }
        catch (IllegalArgumentException e) {
            System.out.println(naoAplicavelRendimentoMensagem + e.getMessage());
        }
        finally {
            System.out.println(operacaoFinalizadaMensagem);
        }

        System.out.println();

        Acao petrobras = new Acao("PETR4", 500);

        System.out.println(ccNatan);

        System.out.println();

        System.out.println(cpWesley);

        System.out.println();

        System.out.println(ceNatan);

        System.out.println();

        System.out.println(ciNatan);

        System.out.println();

        System.out.println(petrobras);

        System.out.println();

        ArrayList<Tributavel> listaTributaveis = new ArrayList<>();

        listaTributaveis.add(ccNatan);

        listaTributaveis.add(cpWesley);

        listaTributaveis.add(petrobras);

        listaTributaveis.add(ceNatan);
        listaTributaveis.add(ciNatan);

        System.out.println("=".repeat(LARGURA));

        System.out.println("       BANCO SATC - RESUMO DE IMPOSTOS");

        System.out.println("=".repeat(LARGURA));

        System.out.println(String.format("%-28s %16s", "ORIGEM", "IMPOSTO (R$)"));

        double total = 0;

        for (Tributavel tributavel : listaTributaveis) {

            double imposto = tributavel.calcularImposto();

            total += imposto;

            String origem = tributavel.getClass().getSimpleName();

            System.out.println(String.format(BR, "%-28.28s %,16.2f", origem, imposto));

        }

        System.out.println("-".repeat(LARGURA));

        System.out.println(String.format(BR, "%-28.28s %,16.2f", "TOTAL", total));

        // Rodapé do resumo.
        System.out.println("=".repeat(LARGURA));

    // Fim do método main().
    }

// Fim da classe Main.
}
