import java.util.ArrayList;

public class Block {
    private String blockID;
    private String prevHash;
    private ArrayList<Transaction> setOfTransactions;

    private Block(String prevHash, ArrayList<Transaction> setOfTransactions) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prevHash);
        for (Transaction transaction : setOfTransactions) {
            stringBuilder.append(transaction.getTransactionID());
        }
        this.blockID = Hash.getHash(stringBuilder.toString());
        this.prevHash = prevHash;
        this.setOfTransactions = setOfTransactions;
    }

    public static Block createBlock(String prevHash, ArrayList<Transaction> setOfTransactions){
        return new Block(prevHash,setOfTransactions);
    }

    public String getPrevHash() {
        return prevHash;
    }

    public String getBlockID() {
        return blockID;
    }

    public ArrayList<Transaction> getSetOfTransactions() {
        return setOfTransactions;
    }
}
