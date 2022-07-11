import java.security.interfaces.DSAPublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private byte[] signData(String message, int index){
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
    public List<DSAPublicKey> getPublicKeys(){
        return wallet.stream().map(DSAKeyPair::getPublicKey).toList();
    }
    public Operation createPaymentOp(Account receiver, int amount, int index){
        return Operation.createOperation(this, receiver, amount, signData(dataToStringForSign(this,receiver,amount),index));
    }
    public static String dataToStringForSign(Account account1, Account account2, int amount){
        return account1.toString()+account2.toString()+amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return balance == account.balance && accountId.equals(account.accountId) && wallet.equals(account.wallet);
    }

    public String getAccountId() {
        return accountId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, wallet, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", wallet=" + wallet +
                ", balance=" + balance +
                '}';
    }
}


