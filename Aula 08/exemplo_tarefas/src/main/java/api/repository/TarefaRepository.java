// Define o pacote responsável pelo acesso a dados.
package api.repository;

// Importa a entidade que este repositório irá persistir.
import api.model.Tarefa;
// Importa a interface pronta de operações CRUD do Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// Declara um repositório para a entidade Tarefa.
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    // O Spring cria a implementação em runtime usando Tarefa como entidade e Long como tipo do id.
    List<Tarefa> findByResponsavelIgnoreCase(String responsavel);
    List<Tarefa> findByConcluidaFalseAndDataPrazoBefore(LocalDate data);
    List<Tarefa> findAllByOrderByDataPrazoAsc();

}
