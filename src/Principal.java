import java.math.BigDecimal; // Importa a classe BigDecimal, usada para operações com números decimais de alta precisão.
import java.time.LocalDate; // Importa a classe LocalDate, usada para representar datas sem tempo.
import java.time.format.DateTimeFormatter; // Importa a classe DateTimeFormatter, usada para formatar e analisar datas.
import java.util.*; // Importa todas as classes do pacote java.util, incluindo coleções como List, Map, etc.
import java.util.stream.Collectors; // Importa a classe Collectors, que contém métodos para coletar resultados de streams.
import java.math.RoundingMode; // Importa a classe RoundingMode, usada para definir modos de arredondamento em operações com BigDecimal.


class Pessoa { // Classe que representa uma pessoa.
    private String nome; // Atributo para armazenar o nome da pessoa.
    private LocalDate dataNascimento; // Atributo para armazenar a data de nascimento da pessoa.

    public Pessoa(String nome, LocalDate dataNascimento) { // Construtor da classe Pessoa, que inicializa o nome e a data de nascimento.
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() { // Método que retorna o nome da pessoa.
        return nome;
    }

    public LocalDate getDataNascimento() { // Método que retorna a data de nascimento da pessoa.
        return dataNascimento;
    }

    public int getIdade() { // Método que calcula e retorna a idade da pessoa com base no ano atual.
        return LocalDate.now().getYear() - dataNascimento.getYear();
    }

    @Override
    public String toString() { // Método que retorna uma representação em String do objeto Pessoa, com o nome e a data de nascimento formatada.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato para a data (dia/mês/ano).
        return "Nome: " + nome + ", Data de Nascimento: " + dataNascimento.format(formatter); // Retorna a string formatada.
    }
}

class Funcionario extends Pessoa { // A classe Funcionario herda a classe Pessoa, ou seja, é uma extensão dela.
    private BigDecimal salario; // Atributo para armazenar o salário do funcionário.
    private String funcao; // Atributo para armazenar a função do funcionário.

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) { // Construtor da classe Funcionario que chama o construtor da classe Pessoa e inicializa salário e função.
        super(nome, dataNascimento); // Chama o construtor da classe Pessoa para inicializar o nome e a data de nascimento.
        this.salario = salario; // Inicializa o salário do funcionário.
        this.funcao = funcao; // Inicializa a função do funcionário.
    }

    public BigDecimal getSalario() { // Método que retorna o salário do funcionário.
        return salario;
    }

    public String getFuncao() { // Método que retorna a função do funcionário.
        return funcao;
    }

    public void aumentarSalario() { // Método para aumentar o salário do funcionário em 10%.
        this.salario = this.salario.multiply(BigDecimal.valueOf(1.10)); // Multiplica o salário atual por 1.10 (aumento de 10%).
    }

    @Override
    public String toString() { // Método que retorna uma representação em String do objeto Funcionario.

        // Formatação do salário para exibição, substituindo as vírgulas por pontos e ajustando o formato para a localidade.
        String salarioFormatado = String.format("%,.2f", salario).replace(",", "X").replace(".", ",").replace("X", ".");

        // Retorna o resultado concatenando as informações de nome, data de nascimento, salário formatado e função.
        return super.toString() + ", Salário: " + salarioFormatado + ", Função: " + funcao;
    }
}


public class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // Remove "João"
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // Imprime todos os funcionários
        funcionarios.forEach(System.out::println);

        // Aumenta o salário em 10%
        funcionarios.forEach(Funcionario::aumentarSalario);

        // Agrupa por função
        Map<String, List<Funcionario>> groupedByFunction = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Imprime os funcionários agrupados
        groupedByFunction.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(System.out::println);
        });

        // Imprime os funcionários com aniversário em Outubro e Dezembro
        funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10 || funcionario.getDataNascimento().getMonthValue() == 12)
                .forEach(System.out::println);

        // Imprime o funcionário mais velho
        Funcionario oldest = funcionarios.stream()
                .max(Comparator.comparingInt(Funcionario::getIdade))
                .orElse(null);
        if (oldest != null) {
            System.out.println("Funcionário mais velho: " + oldest.getNome() + ", Idade: " + oldest.getIdade());
        }

        // Imprime os funcionários em ordem alfabética
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(System.out::println);

        // Imprime o total de salários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total de Salários: " + totalSalarios);

        // Imprime os salários em salários mínimos
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(funcionario.getNome() + " ganha " + salariosMinimos + " salários mínimos.");
        });
    }
}
