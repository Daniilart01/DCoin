import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BlockChain {
    private static BlockChain blockChain = null;
    private HashMap<Account, Integer> coinDatabase;
    private ArrayList<Block> blockHistory;
    private ArrayList<Transaction> txDatabase;
    private int faucetCoins;

    private BlockChain() {
        this.coinDatabase = new HashMap<>();
        this.blockHistory = new ArrayList<>();
        this.txDatabase = new ArrayList<>();
        this.faucetCoins = 10000;
        genesisBlockInit();
    }

    private void genesisBlockInit(){
        final int amount = 20;
        DSAKeyPair dsaKeyPair = DSAKeyPair.getKeyPair();
        Account creator = Account.genAccount(dsaKeyPair);
        Operation operation  = creator.createPaymentOp(creator, amount , 0);
        Transaction transaction = Transaction.createTransaction((ArrayList<Operation>) List.of(operation), 0);
        Block block = Block.createBlock("0".repeat(40), (ArrayList<Transaction>)List.of(transaction));
        txDatabase.add(transaction);
        coinDatabase.put(creator, 20);
        validateBlock(block);
    }

    public void validateBlock(Block block){
        if(!block.getPrevHash().equals(blockHistory.get(blockHistory.size()-1).getBlockID())){
            System.err.println("Breach of integrity, prevHash is illegal!");
            return;
        }
        for (Transaction transaction : block.getSetOfTransactions()) {
            for (Transaction transactionFromTxDatabase : txDatabase) {
                if(transaction.equals(transactionFromTxDatabase)){
                    System.err.println("Transaction already exists!");
                    return;
                }
            }
        }
        for (Transaction transaction : block.getSetOfTransactions()) {
            for (Operation operation : transaction.getSetOfOperations()) {
                if(!Operation.verifyOperation(operation) || operation.getSender().getBalance()<operation.getAmount()){
                    System.err.println("Invalid transaction in block");
                    return;
                }

            }
        }

    }

    private static BlockChain getBlockchain() {
        if (blockChain == null) {
            blockChain = new BlockChain();
        }
        return blockChain;
    }
    public void getTokenFromFaucet(Account account, int amount){
        coinDatabase.put(account,amount);
    }
    public void showCoinDatabase(){
        System.out.println("Coin database:\n");
        for (Map.Entry<Account, Integer> entry : coinDatabase.entrySet()) {
            System.out.println("Account: "+ entry.getKey().getAccountId()+", Balance: "+entry.getValue());
        }
    }
}


