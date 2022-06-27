import java.util.ArrayList;

public class Account {
    private final String accountId;
    private final ArrayList<DSAKeyPair> wallet;
    private int balance;

    private Account(String accountId, ArrayList<DSAKeyPair> wallet, int balance) {
        this.accountId = accountId;
        this.wallet = wallet;
        this.balance = balance;
    }

    public static Account genAccount(DSAKeyPair keyPair) {
        String accountID = Hash.getHash(keyPair.getPublicKey().toString());
        ArrayList<DSAKeyPair> keyPairs = new ArrayList<>();
        keyPairs.add(keyPair);
        int balance = 0;
        return new Account(accountID, keyPairs, balance);
    }
    public void addKeyPairToWallet(DSAKeyPair keyPair){
        wallet.add(keyPair);
    }
    public void updateBalance(int amount){
        if(amount<0){
            if(balance+amount < 0){
                System.out.println("Balance is less than amount to be subtracted");
                return;
            }
        }
        else if(balance+amount<0){
            System.out.println("Balance amount can`t be bigger than Integer's size");
            return;
        }
         balance+=amount;
    }
    public void printBalance(){
        System.out.println("Balance of account: "+accountId +" - "+balance);
    }
    public int getBalance(){
        return balance;
    }
    public byte[] signData(String message, int index){
        DSAKeyPair keyPair;
        try{
            keyPair = wallet.get(index);
        }
        catch (IndexOutOfBoundsException e){
            System.err.println("No such keyPair index in wallet keys list, data will be signed with last keyPair in list");
            keyPair = wallet.get(wallet.size()-1);
        }
        return DSASignature.sign(keyPair.getPrivateKey(), message);
    }
    public Operation createPaymentOp(Account receiver, int amount, int index){
        //TODO: will be realized after creating class Operation on step 4
        return new Operation();
    }

}


