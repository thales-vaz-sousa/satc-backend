package contas;

import java.util.ArrayList;
import java.util.Locale;

import tributaveis.Tributavel;

public abstract class Conta implements Tributavel {

    private static final double ALIQUOTA_IOF = 0.005;
    private static final Locale BR = Locale.of("pt", "BR");
    private static final int LARGURA = 45;

    private String titular;
    private final String numero;
    private double saldo;
    private final ArrayList<String> lancamentos = new ArrayList<>();

    public Conta(String titular, String numero) {
        this.titular = titular;
        this.numero = numero;
    }

    public void depositar(double valor) {
        depositar(valor, "Depósito");
    }

    protected void depositar(double valor, String descricao) {
        if (valor <= 0) return;
        this.saldo += valor;
        registrar(descricao, valor);
    }

    public boolean sacar(double valor) {
        return sacar(valor, "Saque");
    }

    protected boolean sacar(double valor, String descricao) {
        if (valor <= 0 || valor > this.saldo) return false;
        this.saldo -= valor;
        registrar(descricao, -valor);
        return true;
    }

    @Override
    public double calcularImposto() {
        return saldo * ALIQUOTA_IOF;
    }

    private void registrar(String descricao, double valor) {
        this.lancamentos.add(linha(descricao, valor));
    }

    private String linha(String descricao, double valor) {
        return String.format(BR, "%-28.28s %,16.2f", descricao, valor);
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        if (titular.isEmpty()) return;
        this.titular = titular;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        String extrato = "=".repeat(LARGURA) + "\n";
        extrato += "            BANCO SATC - EXTRATO\n";
        extrato += "=".repeat(LARGURA) + "\n";
        extrato += "Titular : " + this.titular + "\n";
        extrato += "Conta   : " + this.numero + "  (" + getClass().getSimpleName() + ")\n";
        extrato += "-".repeat(LARGURA) + "\n";
        extrato += String.format("%-28s %16s", "DESCRIÇÃO", "VALOR (R$)") + "\n";

        for (String lancamento : this.lancamentos) {
            extrato += lancamento + "\n";
        }

        extrato += "-".repeat(LARGURA) + "\n";
        extrato += linha("SALDO", this.saldo) + "\n";
        extrato += linha("Imposto (IOF 0,5%)", calcularImposto()) + "\n";
        extrato += "=".repeat(LARGURA);

        return extrato;
    }
}