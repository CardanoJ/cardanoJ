package Example;

import com.org.cardanocli.backend.transaction.BuildTransaction;
import com.org.cardanocli.backend.wallet.CreateWallet;

public class CLIOprations {
	    public static void main(String[] args) {
	        CreateWallet createWallet = new CreateWallet();   //Wallet Creation
	        createWallet.create();


	        BuildTransaction buildTransaction= new BuildTransaction();  // Build Transaction
	        buildTransaction.transact();
	    }
	}

