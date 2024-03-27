package Example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Redeemer {
	 public static void main(String[] args) {
	        try {
	            String apiKey = "mainnetIYa0p6MpdnX2Y94XEj2Wc3EhOFCqeItV";
	            String txHash = "60ea3afcaa316f0aea5f108f70d64ef35a4c18211a5de6b27a001187e7d11029";
	            String datumhash="65c197d565e88a20885e535f93755682444d3c02fd44dd70883fe89e";
	            URL url = new URL("https://cardano-mainnet.blockfrost.io/api/v0/scripts/datum/"+ datumhash);
	            //URL url = new URL("https://cardano-mainnet.blockfrost.io/api/v0/txs/"+txHash+"/redeemers");
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");
	            connection.setRequestProperty("project_id", apiKey);

	            int responseCode = connection.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                String inputLine;
	                StringBuffer response = new StringBuffer();

	                while ((inputLine = in.readLine()) != null) {
	                    response.append(inputLine);
	                }
	                in.close();

	                // Print the response
	                System.out.println(response.toString());
	            } else {
	                System.out.println("Error: " + responseCode);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

