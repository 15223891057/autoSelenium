async function addCustomNetwork() {
    try {
        const customNetwork = {
            chainId: '0xB56C7', // 网络的链ID（16进制字符串）
            chainName: 'Hemi Sepolia', // 自定义网络的名称
            nativeCurrency: {
                name: 'ETH', // 自定义代币的名称
                symbol: 'ETH', // 自定义代币的符号
                decimals: 18, // 代币的小数位数
            },
            rpcUrls: ['https://testnet.rpc.hemi.network/rpc'], // RPC URL数组
            blockExplorerUrls: ['testnet.explorer.hemi.xyz'], // 区块浏览器URL数组
        };

        // 请求添加自定义网络
        await ethereum.request({
            method: 'wallet_addEthereumChain',
            params: [customNetwork],
        });

        console.log('Network added successfully!');
    } catch (error) {
        console.error('Failed to add network', error);
    }
}

// 调用添加自定义网络的函数
addCustomNetwork();