
package viagemprojeto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.swing.JOptionPane; 

public class Viagemprojeto {

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        String opcao[] = {"Planejar Viagem", "Sair"};
        int escolher = JOptionPane.showOptionDialog(null, "Planejar Viagem", "Trip Planner", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcao, opcao[0]);
        
        if (escolher == 0) {
            planejar();
        } else {
            JOptionPane.showMessageDialog(null, "SISTEMA FINALIZADO", "Trip Planner", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void planejar() {
        String nome = JOptionPane.showInputDialog(null, "Digite o nome do Viajante", "Trip Planner", JOptionPane.QUESTION_MESSAGE);
        if (!campo(nome)) return;

        String dataRaw = JOptionPane.showInputDialog(null, "Digite a data de partida (dd/MM/yyyy): ", "Trip Planner", JOptionPane.QUESTION_MESSAGE);
        if (!campo(dataRaw)) return;
        
        LocalDate data = formatarData(dataRaw);
        if (data == null) {
            JOptionPane.showMessageDialog(null, "Data inválida!");
            return;
        }

        String dias = JOptionPane.showInputDialog(null, "Quantidades de dias viajando: ", "Trip Planner", JOptionPane.QUESTION_MESSAGE);
        if (!campo(dias)) return;
        double diaConvert = converter(dias);

        String valor = JOptionPane.showInputDialog(null, "Valor gasto por dia: ", "Trip Planner", JOptionPane.QUESTION_MESSAGE);
        if (!campo(valor)) return;
        double valorConvert = converter(valor);

        processar(diaConvert, valorConvert, data, nome);
    }

    // Métodos auxiliares que você já tinha criado
    public static boolean campo(String campo) {
        return campo != null && !campo.trim().isEmpty();
    }

    public static double converter(String valor) {
        try {
            return Double.parseDouble(valor.replace(",", "."));
        } catch (Exception e) {
            return 0;
        }
    }

    public static void processar(double dias, double valor, LocalDate dataViagem, String nome) {
        //        Calcular o valor total da viagem: 
//        total = dias * valorPorDia 
        double total = dias * valor; // calculo total de gastos na viagem
        LocalDate hoje = LocalDate.now(); //Pega a data atual do sistema
        String situacao = "";
        if (dataViagem.isBefore(hoje)) {
        situacao = "Viagem Passada";
        JOptionPane.showConfirmDialog(null, "Situação: "+situacao);
        }
        if (dataViagem.isEqual(hoje)) {
        situacao = "Viagem é Hoje!";
        JOptionPane.showConfirmDialog(null, "Situação: "+situacao);
        }
        if (dataViagem.isAfter(hoje)) {
        situacao = "Viagem Futura";
        JOptionPane.showConfirmDialog(null, "Situação: "+situacao);

        long faltamDias = ChronoUnit.DAYS.between(hoje, dataViagem);
        JOptionPane.showMessageDialog(null, "Situação: Viagem Futura\nFaltam " + faltamDias + " dias!");
        }

        resultado(nome, dataViagem, dias, total, situacao);
    }

    public static LocalDate formatarData(String data) {
        try {
            return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            return null;
        }
    }
    public static void resultado(String nome, LocalDate dataViagem, double dias, double valorTotal, String situacao) {
        DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = dataViagem.format(formatoBR);

        String mensagem = String.format(
            "RESUMO DA VIAGEM\n\n" +
            "Viajante: %s\n" +
            "Data da viagem: %s\n" +
            "Dias viajando: %.0f\n" +
            "Valor total: R$ %.2f\n" +
            "Situação: %s", 
            nome, dataFormatada, dias, valorTotal, situacao
        );

        JOptionPane.showMessageDialog(null, mensagem, "Resultado Final", JOptionPane.INFORMATION_MESSAGE);
    }

} 

