"use client";
import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";

export default function Home() {
  // const [cliPath, setCliPath] = useState("");
  const [resourcePath, setResourcePath] = useState("");
  const [name, setName] = useState("");
  const [network, setNetwork] = useState("testnet");
  const [networkId, setNetworkId] = useState("preprod");
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);                                                                                                                         

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setResult(null);

    try {
      const res = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/address/generate`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            // cliPath,
            resourcePath,
            name,
            network,
            networkId: networkId === "preprod" ? "1" : "2",
          }),
        }
      );

      if (!res.ok) throw new Error("Failed to generate address");

      const data = await res.json();

      if (data.error) {
        setError(data.error);
      } else {
        setResult(data);
      }
      // setResult({ content: text });
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-br from-indigo-100 via-blue-50 to-white px-4">
      <motion.div
        initial={{ opacity: 0, scale: 0.95 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{ duration: 0.4 }}
        className="w-full max-w-xl bg-white shadow-2xl rounded-2xl p-10"
      >
        <h1 className="text-3xl font-bold text-gray-900 text-center mb-10">
          🚀 Cardano Address Generator
        </h1>

        <form onSubmit={handleSubmit} className="space-y-6">
          {/* CLI Path */}
          {/* <div className="flex flex-col">
            <label className="text-sm font-semibold text-gray-700 mb-1">
              CLI Path
            </label>
            <input
              type="text"
              value={cliPath}
              onChange={(e) => setCliPath(e.target.value)}
              className="w-full text-black px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:outline-none"
              placeholder="/usr/local/bin/cardano-cli..."
              required
            />
          </div> */}

          {/* Resource Path */}
          <div className="flex flex-col">
            <label className="text-sm font-semibold text-gray-700 mb-1">
              Generated Address Path
            </label>
            <input
              type="text"
              value={resourcePath}
              onChange={(e) => setResourcePath(e.target.value)}
              className="w-full text-black px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:outline-none"
              placeholder="/home/user/cardano/keys/..."
              required
            />
          </div>

          {/* Wallet Name */}
          <div className="flex flex-col">
            <label className="text-sm font-semibold text-gray-700 mb-1">
              Wallet Name
            </label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              className="w-full text-black px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:outline-none"
              placeholder="Your wallet name..."
              required
            />
          </div>

          {/* Network + ID */}
          <div className="grid grid-cols-2 gap-6">
            <div className="flex flex-col">
              <label className="text-sm font-semibold text-gray-700 mb-1">
                Network
              </label>
              <select
                value={network}
                onChange={(e) => setNetwork(e.target.value)}
                className="w-full text-black px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:outline-none"
              >
                <option value="testnet">Testnet</option>
                <option value="mainnet">Mainnet</option>
              </select>
            </div>

            {network === "testnet" && (
              <div className="flex flex-col">
                <label className="text-sm font-semibold text-gray-700 mb-1">
                  Testnet Network ID
                </label>
                <select
                  value={networkId}
                  onChange={(e) => setNetworkId(e.target.value)}
                  className="w-full text-black px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:outline-none"
                >
                  <option value="preprod">Preprod</option>
                  <option value="preview">Preview</option>
                </select>
              </div>
            )}
          </div>

          {/* Submit Button */}
          <motion.button
            type="submit"
            disabled={loading}
            whileHover={!loading ? { scale: 1.03 } : {}}
            whileTap={!loading ? { scale: 0.97 } : {}}
            className={`w-full py-3 rounded-lg text-white font-semibold shadow-md transition-colors ${
              loading
                ? "bg-gray-400 cursor-not-allowed"
                : "bg-indigo-600 hover:bg-indigo-700"
            }`}
          >
            {loading ? "⏳ Generating..." : "Generate Address"}
          </motion.button>
        </form>

        {/* Result + Error Messages */}
        <AnimatePresence>
          {result && (
            <motion.div
              initial={{ opacity: 0, y: 15 }}
              animate={{ opacity: 1, y: 0 }}
              exit={{ opacity: 0 }}
              className="mt-8 p-5 bg-green-50 border border-green-300 rounded-lg text-green-800"
            >
              <p className="font-semibold text-lg">✅ Generated Address:</p>
              <pre className="whitespace-pre-wrap break-all mt-2 text-sm">
                {result.content}
              </pre>
            </motion.div>
          )}

          {error && (
            <motion.div
              initial={{ opacity: 0, y: 15 }}
              animate={{ opacity: 1, y: 0 }}
              exit={{ opacity: 0 }}
              className="mt-8 p-5 bg-red-50 border border-red-300 rounded-lg text-red-800"
            >
              <p className="font-semibold text-lg">❌ Error:</p>
              <p className="mt-1">{error}</p>
            </motion.div>
          )}
        </AnimatePresence>
      </motion.div>
    </div>
  );
}
