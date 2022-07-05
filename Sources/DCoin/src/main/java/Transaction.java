import java.util.ArrayList;

public class Transaction {
    private String transactionID;
    private ArrayList<Operation> setOfOperations;
    private int nonce;

    private Transaction(ArrayList<Operation> setOfOperations, int nonce) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Operation operation : setOfOperations) {
            stringBuilder.append(operation.getOperationID());
        }
        stringBuilder.append(nonce);
        this.transactionID = Hash.getHash(stringBuilder.toString());
        this.setOfOperations = setOfOperations;
        this.nonce = nonce;
    }
    public static Transaction createTransaction(ArrayList<Operation> setOfOperations, int nonce){
        return new Transaction(setOfOperations, nonce);
    }
}
