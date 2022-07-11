import java.util.ArrayList;
import java.util.Objects;

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

    public String getTransactionID() {
        return transactionID;
    }

    public ArrayList<Operation> getSetOfOperations() {
        return setOfOperations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return nonce == that.nonce && transactionID.equals(that.transactionID) && setOfOperations.equals(that.setOfOperations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID, setOfOperations, nonce);
    }
}
