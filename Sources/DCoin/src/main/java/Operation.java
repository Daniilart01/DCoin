import java.security.interfaces.DSAPublicKey;

public class Operation {
    private static long OP_NONCE = 0;
    private String operationID;
    private long nonce;
    private Account sender;
    private Account receiver;
    private int amount;
    private byte[] signature;

    private Operation(Account sender, Account receiver, int amount, byte[] signature){
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.signature = signature;
        nonce = OP_NONCE++;
        operationID = Hash.getHash(sender.toString()+receiver.toString()+amount+nonce);
    }
    public static Operation createOperation(Account sender, Account receiver, int amount, byte[] signature){
        return new Operation(sender,receiver,amount,signature);
    }
    public static boolean verifyOperation(Operation operation){
        if(operation.amount>operation.sender.getBalance()){
            return false;
        }
        if(!operation.operationID.equals(Hash.getHash(operation.sender.toString()+operation.receiver.toString()+operation.amount+operation.nonce))){
            return false;
        }
        for (DSAPublicKey publicKey : operation.sender.getPublicKeys()) {
            if(DSASignature.verify(publicKey, operation.signature, Account.dataToStringForSign(operation.sender, operation.receiver, operation.amount))){
                return true;
            }
        }
        return false;
    }

    public String getOperationID() {
        return operationID;
    }

    public Account getSender() {
        return sender;
    }

    public int getAmount() {
        return amount;
    }
}
