// Este arquivo pertence ao pacote "api" e mora em src/main/java/api/.
// Nome completo desta classe: api.Application
package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/ola")
    public String olaMundoGet() {
        return "Olá, mundo!";
    }

    @GetMapping("/health")
    public String helloHealth() {
        return "Working...";
    }


    @PostMapping("/ola")
    public String olaMundoPost(@RequestBody Pessoa pessoa) {
        return "Olá, " + pessoa.nome() +" - Idade:"+ pessoa.idade()+ "!";
    }
    @DeleteMapping("/ola")
    public String olaMundoDelete(@RequestBody Pessoa pessoa) {
        return "Olá, " + pessoa.nome() +" - Idade:"+ pessoa.idade()+ "! Simulação de Registro "+ pessoa.id() +" excluído com sucesso";
    }

    // Ex 1
    @GetMapping("/tchau")
    public String tchauMundoGet() {
        return "Tchau, mundo!";
    }

    // 1.1
    // Acredito que seja porque assim é usado um biding direto sem a necessidade de criar/colocar um body.
    // Poderia ser no body mas assim fica mais coerente com o padrão REST do verbo e da ação que executa.

    // Ex 2
    @GetMapping("/saudacao/{nome}")
    public String saudacaoGet(@PathVariable String nome) {
        return "Olá, " + nome + "!";
    }

    //2.1
    // Assim o objeto é convertido/serializado corretamente com o tipo correspondente em vez de somente um texto plano.
    // Isso é feito por meio do Spring.

    // Ex 3
    record Numeros(int a, int b) {}
    record Resultado(int soma) {}

    // Ex 3.1
    // Erro 404: não encontrado pq para que eu acesse esse endpoint necessito usar a rota completa.

    // Ex 4
    @PostMapping("/soma")
    public Resultado somaPost(@RequestBody Numeros numeros) {
        return new Resultado(numeros.a() + numeros.b());
    }


    record Pessoa(int id, String nome, int idade) {}

}
