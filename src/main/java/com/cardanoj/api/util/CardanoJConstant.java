package com.cardanoj.api.util;

import org.springframework.stereotype.Component;

/**
 * A utility class containing constants used throughout the Cardano J API application.
 * <p>
 * This class defines various constants such as network types, file paths, and command-line interface (CLI) paths.
 * </p>
 */
@Component
public class CardanoJConstant {

    /**
     * The constant representing the Cardano mainnet.
     */
    public static final String MAINNET = "mainnet";

    /**
     * The constant representing the Cardano testnet.
     */
    public static final String TESTNET = "--testnet-magic";

    /**
     * The file path to the Cardano CLI executable.
     * <p>
     * This path is used to invoke the Cardano CLI commands.
     * </p>
     */
    public static final String cliPath = "/home/quotus/.local/bin/cardano-cli";

    /**
     * The file path to the Cardano node socket.
     * <p>
     * This path is used to connect to the Cardano node via the socket.
     * </p>
     */
    public static final String socketPath = "/home/quotus/git/preview/db/node.socket";

}
