import javax.swing.*;
import java.awt.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class GerarHash {
    File stream;
    byte[] bytesEntradaHash = null, bytesSaidaHash = null;
    StringBuilder hashHexadecimal;
    String hash;
    FileOutputStream saida;
    DataOutputStream escritor;

    public void SistemaArquivos(String pwd) {
        try{
            stream = new File(pwd);
            if(stream.exists()){
                JOptionPane.showMessageDialog(null,"Nome: " + stream.getName()
                        + "\nTamanho: " + stream.length() + " bytes\nE um arquivo? " + stream.isFile()
                        + "\nE um diretorio? " + stream.isDirectory() + "\nPode acessar para ler? " + stream.canRead()
                        ,"Arquivo Encontrado", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,
                "Arquivo (" + stream + ") não encontrado!!!","Atenção!!!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
    }
    // /home/lucasomar/Development/JavaGeradorHash
    // fazer salvar todos os paths em um arquivo
    // recursivamente ler cada local e gerar a hash do arquivo
    public void MethodDisplayContent(File diretorio, String hsh) throws IOException, NoSuchAlgorithmException {
        GerarHash generate = new GerarHash();
        File[] files = diretorio.listFiles();

            for(File file : files){
                String fullPath = file.getCanonicalPath(); // recebe o caminho completo do diretório
                generate.MethodCalculaHash(fullPath, hsh);
                if (file.isDirectory()){

                }
            }
    }

    public String MethodCalculaHash(String pwd, String hsh) throws NoSuchAlgorithmException {
        File arquivo = new File(pwd);
        try {
            bytesEntradaHash = Files.readAllBytes(arquivo.toPath());
            MessageDigest algoritmoHash = MessageDigest.getInstance(hsh);
            bytesSaidaHash = algoritmoHash.digest(bytesEntradaHash);

            hashHexadecimal = new StringBuilder();
            for (byte b : bytesSaidaHash) {
                hashHexadecimal.append(String.format("%02X", 0xFF & b));
            }

            hash = hashHexadecimal.toString();
//            System.out.println("\nSequência de Bytes da HASH Gerada pelo Algoritmo " + hash);
            System.out.printf("\n" + "[" + hsh +"] "+ hash +"\t→ " + pwd );
            //            MethodGeraArquivo(pwd, hash);


        } catch (IOException erro) {}
        return hash;
    }

    public void MethodGeraArquivo(String pwd,String hash){
        try{
            saida = new FileOutputStream("./log.txt");
            escritor = new DataOutputStream(saida);
            escritor.writeUTF("\n" + pwd + "\t" + hash + "\n");
            saida.close();
        }
        catch (IOException erro){}
    }

    public static void main(String pwd, String hsh) throws NoSuchAlgorithmException, IOException {
        GerarHash GH = new GerarHash();

        GH.SistemaArquivos(pwd);
        GH.MethodDisplayContent(new File(pwd), hsh);

        String hash = GH.MethodCalculaHash(pwd,hsh);
//        GH.MethodGeraArquivo(pwd,hash);
    }
}
