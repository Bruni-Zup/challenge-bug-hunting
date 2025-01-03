import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Video {
    // Encapsulando os atributos, tornando-os privados.
    private String titulo;
    private String descricao;
    private int duracao; // em minutos
    private String categoria;
    private Date dataPublicacao;

    // Construtor da classe Video
    public Video(String titulo, String descricao, int duracao, String categoria, Date dataPublicacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.duracao = duracao;
        this.categoria = categoria;
        this.dataPublicacao = dataPublicacao;
    }

    // Getters e Setters para acessar e modificar os atributos de forma controlada
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    // Método toString para representar o objeto como uma String
    @Override
    public String toString() {
        // Criando a instância do SimpleDateFormat localmente para evitar problemas de thread safety
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return titulo + ";" + descricao + ";" + duracao + ";" + categoria + ";" + sdf.format(dataPublicacao);
    }

    // Método estático que cria um objeto Video a partir de uma String
    public static Video fromString(String linha) {
        try {
            // Dividindo a linha com base no separador ";"
            String[] partes = linha.split(";");
            if (partes.length != 5) {
                throw new IllegalArgumentException("Formato da linha inválido. Esperado 5 campos.");
            }

            // Criando a instância do SimpleDateFormat localmente
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Tentando analisar a data e converter a duração para um inteiro
            return new Video(partes[0], partes[1], Integer.parseInt(partes[2]), partes[3], sdf.parse(partes[4]));
        } catch (NumberFormatException e) {
            // Caso a conversão de número falhe (duracao não for um número válido)
            System.err.println("Erro ao converter a duração para número: " + e.getMessage());
            return null; // Retorna null se houver erro ao converter a duração
        } catch (ParseException e) {
            // Caso falhe ao tentar analisar a data
            System.err.println("Erro ao formatar a data: " + e.getMessage());
            return null; // Retorna null se houver erro na conversão de data
        } catch (IllegalArgumentException e) {
            // Caso o formato da linha esteja incorreto
            System.err.println(e.getMessage());
            return null; // Retorna null em caso de erro de formato
        }
    }
}
