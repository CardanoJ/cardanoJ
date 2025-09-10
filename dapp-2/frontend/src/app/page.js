"use client";

import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import {
  Send,
  Wallet,
  ArrowRight,
  CheckCircle,
  AlertCircle,
  Loader,
} from "lucide-react";

const CardanoTransactionForm = () => {
  const [formData, setFormData] = useState({
    resourcePath: "",
    senderAddress: "",
    receiverAddress: "",
    network: "testnet",
    networkId: "1",
    lovelaceAmount: "",
    senderName: "",
  });

  const [formErrors, setFormErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
    setFormErrors((prev) => ({ ...prev, [name]: "" })); // clear error on typing
  };

  const validateForm = () => {
    const errors = {};

    if (!formData.resourcePath.trim()) {
      errors.resourcePath = "Resource path is required";
    } else if (!formData.resourcePath.startsWith("/")) {
      errors.resourcePath = "Resource path must start with '/'";
    }

    if (!formData.senderName.trim()) {
      errors.senderName = "Signing key name is required";
    }

    if (!formData.senderAddress.trim()) {
      errors.senderAddress = "Sender address is required";
    } else if (!formData.senderAddress.startsWith("addr")) {
      errors.senderAddress = "Invalid sender address format";
    }

    if (!formData.receiverAddress.trim()) {
      errors.receiverAddress = "Receiver address is required";
    } else if (!formData.receiverAddress.startsWith("addr")) {
      errors.receiverAddress = "Invalid receiver address format";
    }

    if (!formData.network) {
      errors.network = "Network is required";
    }

    if (formData.network === "testnet" && !formData.networkId) {
      errors.networkId = "Network ID is required for testnet";
    }

    if (!formData.lovelaceAmount) {
      errors.lovelaceAmount = "Amount is required";
    } else if (isNaN(formData.lovelaceAmount) || formData.lovelaceAmount <= 0) {
      errors.lovelaceAmount = "Amount must be a positive number";
    }

    setFormErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleSubmit = async () => {
    if (!validateForm()) return;

    let fixedPath = formData.resourcePath;
    if (fixedPath && !fixedPath.endsWith("/")) {
      fixedPath += "/";
    }

    const payload = {
      ...formData,
      resourcePath: fixedPath,
    };

    console.log(payload);

    try {
      setIsSubmitting(true);
      setError(null);
      setResult(null);
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_API_URL}/transaction/direct`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(payload),
        }
      );

      const data = await response.json();

      if (response.ok) {
        setResult(data.content);
      } else {
        setError(data.content || "Transaction failed");
      }
    } catch (err) {
      setError("Network error: " + err.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  const errorStyle = "text-red-400 text-xs mt-1";

  const containerVariants = {
    hidden: { opacity: 0, y: 20 },
    visible: {
      opacity: 1,
      y: 0,
      transition: {
        duration: 0.6,
        staggerChildren: 0.1,
      },
    },
  };

  const itemVariants = {
    hidden: { opacity: 0, x: -20 },
    visible: {
      opacity: 1,
      x: 0,
      transition: { duration: 0.4 },
    },
  };

  const buttonVariants = {
    hover: {
      scale: 1.02,
      transition: { duration: 0.2 },
    },
    tap: {
      scale: 0.98,
    },
  };

  const resultVariants = {
    hidden: { opacity: 0, scale: 0.8 },
    visible: {
      opacity: 1,
      scale: 1,
      transition: {
        type: "spring",
        stiffness: 300,
        damping: 20,
      },
    },
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-slate-800 flex items-center justify-center p-4">
      <motion.div
        variants={containerVariants}
        initial="hidden"
        animate="visible"
        className="w-full max-w-2xl"
      >
        {/* Header */}
        <motion.div variants={itemVariants} className="text-center mb-8">
          <div className="inline-flex items-center justify-center w-16 h-16 bg-gradient-to-r from-blue-500 to-cyan-500 rounded-full mb-4">
            <Wallet className="w-8 h-8 text-white" />
          </div>
          <h1 className="text-4xl font-bold text-white mb-2">
            Cardano Transaction
          </h1>
          <p className="text-slate-300">
            Send ADA securely on the Cardano network
          </p>
        </motion.div>

        {/* Main Card */}
        <motion.div
          variants={itemVariants}
          className="bg-white/10 backdrop-blur-lg rounded-3xl p-8 border border-white/20 shadow-2xl"
        >
          <div className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <motion.div variants={itemVariants} className="space-y-2">
                <label className="block text-sm font-semibold text-white">
                  Resource Path
                </label>
                <input
                  type="text"
                  name="resourcePath"
                  value={formData.resourcePath}
                  onChange={handleInputChange}
                  className="w-full px-4 py-3 bg-white/10 border border-white/20 rounded-xl text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent transition-all duration-200"
                  placeholder="/path/to/resources/"
                  required
                />
                {formErrors.resourcePath && (
                  <p className={errorStyle}>{formErrors.resourcePath}</p>
                )}
              </motion.div>

              <motion.div variants={itemVariants} className="space-y-2">
                <label className="block text-sm font-semibold text-white">
                  Signing Key Name
                </label>
                <input
                  type="text"
                  name="senderName"
                  value={formData.senderName}
                  onChange={handleInputChange}
                  className="w-full px-4 py-3 bg-white/10 border border-white/20 rounded-xl text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent transition-all duration-200"
                  placeholder="(yourName).skey..."
                  required
                />
                {formErrors.senderName && (
                  <p className={errorStyle}>{formErrors.senderName}</p>
                )}
              </motion.div>
            </div>

            <motion.div variants={itemVariants} className="space-y-2">
              <label className="block text-sm font-semibold text-white">
                Sender Address
              </label>
              <input
                type="text"
                name="senderAddress"
                value={formData.senderAddress}
                onChange={handleInputChange}
                className="w-full px-4 py-3 bg-white/10 border border-white/20 rounded-xl text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent transition-all duration-200"
                placeholder="addr1..."
                required
              />
              {formErrors.senderAddress && (
                <p className={errorStyle}>{formErrors.senderAddress}</p>
              )}
            </motion.div>

            <motion.div variants={itemVariants} className="space-y-2">
              <label className="block text-sm font-semibold text-white">
                Receiver Address
              </label>
              <input
                type="text"
                name="receiverAddress"
                value={formData.receiverAddress}
                onChange={handleInputChange}
                className="w-full px-4 py-3 bg-white/10 border border-white/20 rounded-xl text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent transition-all duration-200"
                placeholder="addr1..."
                required
              />
              {formErrors.receiverAddress && (
                <p className={errorStyle}>{formErrors.receiverAddress}</p>
              )}
            </motion.div>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              <motion.div variants={itemVariants} className="space-y-2">
                <label className="block text-sm font-semibold text-white">
                  Network
                </label>
                <select
                  name="network"
                  value={formData.network}
                  onChange={handleInputChange}
                  className="w-full px-4 py-3 bg-white/10 border border-white/20 rounded-xl text-white focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent transition-all duration-200"
                >
                  <option value="testnet" className="bg-slate-800">
                    Testnet
                  </option>
                  <option value="mainnet" className="bg-slate-800">
                    Mainnet
                  </option>
                </select>
                {formErrors.network && (
                  <p className={errorStyle}>{formErrors.network}</p>
                )}
              </motion.div>

              {formData.network === "testnet" && (
                <motion.div variants={itemVariants} className="space-y-2">
                  <label className="block text-sm font-semibold text-white">
                    Network ID
                  </label>
                  <select
                    name="networkId"
                    value={formData.networkId}
                    onChange={handleInputChange}
                    className="w-full px-4 py-3 bg-white/10 border border-white/20 rounded-xl text-white focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent transition-all duration-200"
                  >
                    <option value="1" className="bg-slate-800">
                      Preprod
                    </option>
                    <option value="2" className="bg-slate-800">
                      Preview
                    </option>
                  </select>
                  {formErrors.networkId && (
                    <p className={errorStyle}>{formErrors.networkId}</p>
                  )}
                </motion.div>
              )}

              <motion.div variants={itemVariants} className="space-y-2">
                <label className="block text-sm font-semibold text-white">
                  Amount (Lovelace)
                </label>
                <input
                  type="number"
                  name="lovelaceAmount"
                  value={formData.lovelaceAmount}
                  onChange={handleInputChange}
                  className="w-full px-4 py-3 bg-white/10 border border-white/20 rounded-xl text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent transition-all duration-200"
                  placeholder="1000000"
                  required
                  min="1"
                />
                {formErrors.lovelaceAmount && (
                  <p className={errorStyle}>{formErrors.lovelaceAmount}</p>
                )}
              </motion.div>
            </div>

            <motion.button
              variants={buttonVariants}
              whileHover="hover"
              whileTap="tap"
              disabled={isSubmitting}
              onClick={handleSubmit}
              className="w-full py-4 bg-gradient-to-r from-blue-500 to-cyan-500 hover:from-blue-600 hover:to-cyan-600 text-white font-bold rounded-xl shadow-lg disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-200 flex items-center justify-center space-x-2"
            >
              {isSubmitting ? (
                <>
                  <Loader className="w-5 h-5 animate-spin" />
                  <span>Processing Transaction...</span>
                </>
              ) : (
                <>
                  <Send className="w-5 h-5" />
                  <span>Send Transaction</span>
                  <ArrowRight className="w-5 h-5" />
                </>
              )}
            </motion.button>
          </div>
        </motion.div>

        {/* Results */}
        <AnimatePresence mode="wait">
          {(result || error) && (
            <motion.div
              variants={resultVariants}
              initial="hidden"
              animate="visible"
              exit="hidden"
              className="mt-6"
            >
              {result && (
                <div className="bg-green-500/20 border border-green-500/30 rounded-2xl p-6 backdrop-blur-sm">
                  <div className="flex items-start space-x-3">
                    <CheckCircle className="w-6 h-6 text-green-400 mt-1 flex-shrink-0" />
                    <div>
                      <h3 className="text-lg font-semibold text-green-300 mb-2">
                        Transaction Successful!
                      </h3>
                      <p className="text-green-100 text-sm font-mono break-all bg-green-500/10 p-3 rounded-lg">
                        {result}
                      </p>
                    </div>
                  </div>
                </div>
              )}

              {error && (
                <div className="bg-red-500/20 border border-red-500/30 rounded-2xl p-6 backdrop-blur-sm">
                  <div className="flex items-start space-x-3">
                    <AlertCircle className="w-6 h-6 text-red-400 mt-1 flex-shrink-0" />
                    <div>
                      <h3 className="text-lg font-semibold text-red-300 mb-2">
                        Transaction Failed
                      </h3>
                      <p className="text-red-100 text-sm">{error}</p>
                    </div>
                  </div>
                </div>
              )}
            </motion.div>
          )}
        </AnimatePresence>

        {/* Footer */}
        <motion.div
          variants={itemVariants}
          className="text-center mt-8 text-slate-400 text-sm"
        >
          <p>Powered by AIQuant • Built with ❤️</p>
        </motion.div>
      </motion.div>
    </div>
  );
};

export default CardanoTransactionForm;
