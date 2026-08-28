package contas;

public class ContaInvestimento extends Conta {

    private static final double ALIQUOTA_IR = 0.225;

    private double totalAplicado;

    public ContaInvestimento(String titular, String numero) {

        super(titular, numero);
    }

    @Override
    protected void depositar(double valor, String descricao) {

        super.depositar(valor, descricao);

        if (valor > 0) this.totalAplicado += valor;

    }

    public void aplicarRendimento(double percentual) {

        if (percentual <= 0) {
            throw new IllegalArgumentException("Percentual de rendimento precisa ser positivo");
        }

        super.depositar(getSaldo() * percentual, "Rendimento (" + (percentual * 100) + "%)");

    }

    @Override
    public double calcularImposto() {

        double lucro = getSaldo() - totalAplicado;

        return lucro > 0 ? lucro * ALIQUOTA_IR : 0;

    }

    @Override
    public String tipoDeConta() {

        return "Investimento";

    }
}
