package com.x.autoselenium.metamask;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.x.autoselenium.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws InterruptedException {
//        String str = "[{\"address\":\"0xe3a21e8bbcdf74fd6c7c76ad0ba4241b943a2d2d\",\"sn\":\"107\"},{\"address\":\"0x3c0daed63ee3c1f2dfe336099e81f474ff5d1c05\",\"sn\":\"64\"},{\"address\":\"0x44ec7fb06033d86f4a2bfd1b99e8d47c9b0d86e6\",\"sn\":\"96\"},{\"address\":\"0x3e1620a6ee38ad328c4a56aff6d41a6f412cbc79\",\"sn\":\"63\"},{\"address\":\"0x5073e2e1990340c82087af1f98bd2112263762ff\",\"sn\":\"38\"},{\"address\":\"0x940e60de2ce83d0a91143603edd6e4dc2948a27f\",\"sn\":\"83\"},{\"address\":\"0x580a06108072e5da0eb663e59fd56ad5bce93078\",\"sn\":\"92\"},{\"address\":\"0x240e3094b1db4d60d998a08f5b182edebfd0a959\",\"sn\":\"79\"},{\"address\":\"0xe1452f050a1953618dc7ff97f3390c36799c1f02\",\"sn\":\"91\"},{\"address\":\"0x5a2f0e07fb993ef369018916d8d0fe7cccbc50cf\",\"sn\":\"100\"},{\"address\":\"0x10f48bf75516895f2e07dc74bd05189144790bdf\",\"sn\":\"58\"},{\"address\":\"0x2839dd186ee92c4c95bfe466d73f6ee6167be176\",\"sn\":\"51\"},{\"address\":\"0x3b715228522152e786a2306a5a593dde65f17af0\",\"sn\":\"102\"},{\"address\":\"0x0127c56fba55b5b1ec51e5977cffd212c5439112\",\"sn\":\"20\"},{\"address\":\"0xbcc545e884c5c3e04b2460922a8e1328e970d5a5\",\"sn\":\"78\"},{\"address\":\"0x385d34caca2de62b8b1f7f1f08a3d6ac3a051bb6\",\"sn\":\"21\"},{\"address\":\"0x41488a2e1fb0e81825879712f759ffed76fe4a41\",\"sn\":\"80\"},{\"address\":\"0x744e39d636af71d9d457af22d48f6356b0950e88\",\"sn\":\"22\"},{\"address\":\"0x9859810f74729faed5c34d3e2604b76ddd3e9a41\",\"sn\":\"86\"},{\"address\":\"0xdf9705217be0cf3ceca7357d8a80d9b4c0f53a95\",\"sn\":\"7\"},{\"address\":\"0x032a36af45a3487be1e4ded8691a5953fb8cf523\",\"sn\":\"45\"},{\"address\":\"0x46890b65f2255545096b22124a7503efbc51dfea\",\"sn\":\"43\"},{\"address\":\"0x64a286c80cb4d1c34332fdd83c6a1670692a8abf\",\"sn\":\"103\"},{\"address\":\"0x4e3a7260dda3ffe841be315481677c5f0b9fc27a\",\"sn\":\"37\"},{\"address\":\"0x85d522cdcda3781318e81eb4a27ea1d2e274a909\",\"sn\":\"99\"},{\"address\":\"0x55080c7c4afcc84fcf87db08afe8b4ff30b79566\",\"sn\":\"55\"},{\"address\":\"0xa5e776c6110142afc602cb7239338401279b7b67\",\"sn\":\"88\"},{\"address\":\"0xffe9190e65391c897c729bbf281a1d6364c3caeb\",\"sn\":\"46\"},{\"address\":\"0x6a1b0ef703314ea9339cf904dd44525689efa945\",\"sn\":\"59\"},{\"address\":\"0x17bd716563a8e7d47aaeedbb100b2568c26adf6d\",\"sn\":\"61\"},{\"address\":\"0xefac0053a796fc63f6ecda4e0c5306a52b626d6f\",\"sn\":\"9\"},{\"address\":\"0x34130aece0a161d0e26e7ca5a2e66d606af9b435\",\"sn\":\"97\"},{\"address\":\"0x1be96ab6b2464b18d1b2d42f5158a7d9cdbc6bc5\",\"sn\":\"41\"},{\"address\":\"0x05de2077dc473afc5ad9be4fc9b32bf1e1d01ee8\",\"sn\":\"33\"},{\"address\":\"0x4379545926b9bcd8399ff0508cce40d6aacda91c\",\"sn\":\"12\"},{\"address\":\"0x552df6eb9e8a27a155707738cbaca7713a1ba16e\",\"sn\":\"77\"},{\"address\":\"0x6133c15fe929b03d6e077234aa1af66c567d024b\",\"sn\":\"106\"},{\"address\":\"0x33cf3775a80f8f1ada2519738bf037afd1dd8692\",\"sn\":\"53\"},{\"address\":\"0x1b4fbb5c26cf2103368682fb77b6f6f7bd62c846\",\"sn\":\"87\"},{\"address\":\"0x49705c48566f58d219666ea28c89fef8547cba56\",\"sn\":\"47\"},{\"address\":\"0x682a66843a9ca955f18d82ac4d4547ea6d384df3\",\"sn\":\"8\"},{\"address\":\"0x436cfa2dc21dc662e25b5a76680b01a1c5de0c55\",\"sn\":\"42\"},{\"address\":\"0xf196de16691a8bc1e24996e495d2ca6847ff9cae\",\"sn\":\"26\"},{\"address\":\"0x4d05c9bd9aef5f624aa937499bc2ecdeab551121\",\"sn\":\"39\"},{\"address\":\"0xbe2808e4aef04a150760d51dee1f1fa45947195b\",\"sn\":\"98\"},{\"address\":\"0x714b32df6d1241e8b9ccce1527b36ad41ebeaddd\",\"sn\":\"90\"},{\"address\":\"0xca16c0f4edc7c4dabd5ee1bb0644ed99a4e5dc4e\",\"sn\":\"3\"},{\"address\":\"0xc11dbd8f941791bf16715b69b805f854efb42a2b\",\"sn\":\"95\"},{\"address\":\"0x53f5709babb658acc89577b0d37c4a8c9e466226\",\"sn\":\"75\"},{\"address\":\"0x151d0bb864c935e628e39fc49b3f84e1dae50bc2\",\"sn\":\"67\"},{\"address\":\"0x202851451c1f7c08b9939444a0de27498c897ba2\",\"sn\":\"62\"},{\"address\":\"0xe0374349616385c63177798e4cdd07201bbed1ed\",\"sn\":\"32\"},{\"address\":\"0xd4e506906df9464041cd97553f31304e8da28201\",\"sn\":\"10\"},{\"address\":\"0xebd859f84202183583dd70ae3045772409092031\",\"sn\":\"49\"},{\"address\":\"0xd2ba2c2c2e06c41d70bf221561a7703f74869c15\",\"sn\":\"44\"},{\"address\":\"0xa84c28a61a76a7bef8a07d5073921b95e201b449\",\"sn\":\"50\"},{\"address\":\"0xd0829c6180df3dafd8661d68c476b965321cd3f1\",\"sn\":\"60\"},{\"address\":\"0x69e049d806b366e7287b9cf50d9cb8735761b601\",\"sn\":\"68\"},{\"address\":\"0x4d65710b7a2cf9ae686c353cb3f082a3c4cf7357\",\"sn\":\"109\"},{\"address\":\"0x43b69d43f082d1b564039c864b24ed5452372509\",\"sn\":\"70\"},{\"address\":\"0xa17e40c3dc6ec97c08d471de7b2130ceb097bd8e\",\"sn\":\"110\"},{\"address\":\"0xc6d28e43b836effc5e5aba335ab6ed64bfbdc10b\",\"sn\":\"66\"},{\"address\":\"0x8c32c1023b439ca949d1879f0e13d201dc832789\",\"sn\":\"93\"},{\"address\":\"0x03fb1d29c32a55c5f4534b14972ad5ceb51c8d3d\",\"sn\":\"11\"},{\"address\":\"0x94a505ff5ecb7fe4e8fb9f3decde869a66b87296\",\"sn\":\"30\"},{\"address\":\"0x00e529330c63fbfe25b2ec43d34980b9b79ba31a\",\"sn\":\"27\"},{\"address\":\"0x6827c71c11010b62f1d6885ba51dc14ae49bdc09\",\"sn\":\"34\"},{\"address\":\"0xf576601613590e350f16058bb7e93f9f57594edd\",\"sn\":\"35\"},{\"address\":\"0x37fc6d614559c0683a483bd94b9594d23e4e306c\",\"sn\":\"6\"},{\"address\":\"0xd1613784b7215c8804643acc539a68087822e8e3\",\"sn\":\"72\"},{\"address\":\"0x5dce34737db0b43ca75aa74a83461de30bfb66db\",\"sn\":\"76\"},{\"address\":\"0xaa6c55b236b200d9b8dc0d19e2481e43b6b51c5e\",\"sn\":\"104\"},{\"address\":\"0x234297d179d60c9b63982eaa7e488b1fcf26185b\",\"sn\":\"57\"},{\"address\":\"0xf44cd34fb89c540517a86c2d5341468be9479df5\",\"sn\":\"18\"},{\"address\":\"0x2315531435c8f59f3d28646585728be50fc082ce\",\"sn\":\"25\"},{\"address\":\"0x1f897f519d986269c96c69580f67cfd2f339810b\",\"sn\":\"101\"},{\"address\":\"0xfc58270e05b988f6dade1970e8db6efee156e5ce\",\"sn\":\"105\"},{\"address\":\"0xd3e2167e72318d1c56bad0dcc067a04ea1285597\",\"sn\":\"24\"},{\"address\":\"0x63a375b7c85b897ebad7f3cc85a82cccf31649f3\",\"sn\":\"54\"},{\"address\":\"0xac7e94f496aaa34e47ebf97a354b6775f64bfd1b\",\"sn\":\"82\"},{\"address\":\"0xf016ed046965c6c41f3653803f46ecc35d729e78\",\"sn\":\"56\"},{\"address\":\"0x086b03b8d353f42c178c45649b7660d67f2a1ee0\",\"sn\":\"81\"},{\"address\":\"0x8e77c5019dfd0904d0a4f3aa0505961bb17eb0b6\",\"sn\":\"29\"},{\"address\":\"0x9a4c84f6eb45add3f4e40e72f75df5164e7f872c\",\"sn\":\"5\"},{\"address\":\"0x3613e115244c87b367febac56855bda2e8688f49\",\"sn\":\"73\"},{\"address\":\"0x9c88c3028d2e2bdacec86078aa3ae238350a43ec\",\"sn\":\"74\"},{\"address\":\"0xb9ebb76676576805b14d5ca068d6c52da3c8ccf7\",\"sn\":\"71\"},{\"address\":\"0xa943220eec0fdf1b365a89a493448d870e3d0fd1\",\"sn\":\"84\"},{\"address\":\"0x8dd382c74b3666416485e9f3b9fd137729402a08\",\"sn\":\"108\"},{\"address\":\"0x22601c341d9db660a982957496820ff5242ac9f5\",\"sn\":\"69\"},{\"address\":\"0x40bfd863227b04b4ff1cd71f28c32f1ac5393c3b\",\"sn\":\"36\"},{\"address\":\"0x0404150590d8dd7a832d2d926096c3a868c37b6f\",\"sn\":\"23\"},{\"address\":\"0xbaeb8524f82d45783f5ee20e0a8f42b898e89e19\",\"sn\":\"89\"},{\"address\":\"0x3e2ea4f76374159050e6a91dc5572e2435c901ca\",\"sn\":\"31\"},{\"address\":\"0x1aaf1f447a59a3df30c5b59ab5ed377b867c690c\",\"sn\":\"19\"},{\"address\":\"0x77c0cc61a796586376e0694559797d1948872b80\",\"sn\":\"85\"},{\"address\":\"0x46193456cb458329e25053ba33eb288a3af210e0\",\"sn\":\"94\"},{\"address\":\"0x988d298cc7da03bfb147dac52fb7c7136ffc62ab\",\"sn\":\"48\"},{\"address\":\"0xa24c734a8606bf751108fb6cb6d481df8644c535\",\"sn\":\"65\"},{\"address\":\"0x2fcb0e6b25be5d1901542641ac4dc10397c3059f\",\"sn\":\"52\"}]";
        String str = "[{\"address\":\"0x086b03b8d353f42c178c45649b7660d67f2a1ee0\",\"user_id\":\"jj12b68\",\"sn\":\"81\"},{\"address\":\"0x9a4c84f6eb45add3f4e40e72f75df5164e7f872c\",\"user_id\":\"jh4ynmp\",\"sn\":\"5\"},{\"address\":\"0x44ec7fb06033d86f4a2bfd1b99e8d47c9b0d86e6\",\"user_id\":\"jj14ixk\",\"sn\":\"96\"},{\"address\":\"0x552df6eb9e8a27a155707738cbaca7713a1ba16e\",\"user_id\":\"jj12b64\",\"sn\":\"77\"},{\"address\":\"0xa943220eec0fdf1b365a89a493448d870e3d0fd1\",\"user_id\":\"jj12b6d\",\"sn\":\"84\"},{\"address\":\"0x032a36af45a3487be1e4ded8691a5953fb8cf523\",\"user_id\":\"jihns3j\",\"sn\":\"45\"},{\"address\":\"0x8e77c5019dfd0904d0a4f3aa0505961bb17eb0b6\",\"user_id\":\"jhoybmm\",\"sn\":\"29\"},{\"address\":\"0x2fcb0e6b25be5d1901542641ac4dc10397c3059f\",\"user_id\":\"jihnsla\",\"sn\":\"52\"},{\"address\":\"0xfc58270e05b988f6dade1970e8db6efee156e5ce\",\"user_id\":\"jj14ixt\",\"sn\":\"105\"},{\"address\":\"0x8dd382c74b3666416485e9f3b9fd137729402a08\",\"user_id\":\"jj14ixw\",\"sn\":\"108\"},{\"address\":\"0xbaeb8524f82d45783f5ee20e0a8f42b898e89e19\",\"user_id\":\"jj12b6i\",\"sn\":\"89\"},{\"address\":\"0x10f48bf75516895f2e07dc74bd05189144790bdf\",\"user_id\":\"jihnsln\",\"sn\":\"58\"},{\"address\":\"0xb9ebb76676576805b14d5ca068d6c52da3c8ccf7\",\"user_id\":\"jj104tp\",\"sn\":\"71\"},{\"address\":\"0xac7e94f496aaa34e47ebf97a354b6775f64bfd1b\",\"user_id\":\"jj12b6a\",\"sn\":\"82\"},{\"address\":\"0xd4e506906df9464041cd97553f31304e8da28201\",\"user_id\":\"jh5i4fq\",\"sn\":\"10\"},{\"address\":\"0x33cf3775a80f8f1ada2519738bf037afd1dd8692\",\"user_id\":\"jihnslc\",\"sn\":\"53\"},{\"address\":\"0x37fc6d614559c0683a483bd94b9594d23e4e306c\",\"user_id\":\"jh50yao\",\"sn\":\"6\"},{\"address\":\"0x64a286c80cb4d1c34332fdd83c6a1670692a8abf\",\"user_id\":\"jj14ixr\",\"sn\":\"103\"},{\"address\":\"0xd3e2167e72318d1c56bad0dcc067a04ea1285597\",\"user_id\":\"jhmf71j\",\"sn\":\"24\"},{\"address\":\"0xa24c734a8606bf751108fb6cb6d481df8644c535\",\"user_id\":\"jj104tj\",\"sn\":\"65\"},{\"address\":\"0x22601c341d9db660a982957496820ff5242ac9f5\",\"user_id\":\"jj104tn\",\"sn\":\"69\"},{\"address\":\"0x6827c71c11010b62f1d6885ba51dc14ae49bdc09\",\"user_id\":\"ji70vcr\",\"sn\":\"34\"},{\"address\":\"0x0127c56fba55b5b1ec51e5977cffd212c5439112\",\"user_id\":\"jhm7jjr\",\"sn\":\"20\"},{\"address\":\"0xe1452f050a1953618dc7ff97f3390c36799c1f02\",\"user_id\":\"jj14ixd\",\"sn\":\"91\"},{\"address\":\"0xaa6c55b236b200d9b8dc0d19e2481e43b6b51c5e\",\"user_id\":\"jj14ixs\",\"sn\":\"104\"},{\"address\":\"0xc6d28e43b836effc5e5aba335ab6ed64bfbdc10b\",\"user_id\":\"jj104tk\",\"sn\":\"66\"},{\"address\":\"0x240e3094b1db4d60d998a08f5b182edebfd0a959\",\"user_id\":\"jj12b66\",\"sn\":\"79\"},{\"address\":\"0x714b32df6d1241e8b9ccce1527b36ad41ebeaddd\",\"user_id\":\"jj12b6j\",\"sn\":\"90\"},{\"address\":\"0xe0374349616385c63177798e4cdd07201bbed1ed\",\"user_id\":\"ji70vcp\",\"sn\":\"32\"},{\"address\":\"0x4d65710b7a2cf9ae686c353cb3f082a3c4cf7357\",\"user_id\":\"jj14ixx\",\"sn\":\"109\"},{\"address\":\"0x436cfa2dc21dc662e25b5a76680b01a1c5de0c55\",\"user_id\":\"jihns3g\",\"sn\":\"42\"},{\"address\":\"0x151d0bb864c935e628e39fc49b3f84e1dae50bc2\",\"user_id\":\"jj104tl\",\"sn\":\"67\"},{\"address\":\"0x00e529330c63fbfe25b2ec43d34980b9b79ba31a\",\"user_id\":\"jhmf7fo\",\"sn\":\"27\"},{\"address\":\"0xc11dbd8f941791bf16715b69b805f854efb42a2b\",\"user_id\":\"jj14ixj\",\"sn\":\"95\"},{\"address\":\"0x0404150590d8dd7a832d2d926096c3a868c37b6f\",\"user_id\":\"jhmf6uw\",\"sn\":\"23\"},{\"address\":\"0x5073e2e1990340c82087af1f98bd2112263762ff\",\"user_id\":\"ji70vcv\",\"sn\":\"38\"},{\"address\":\"0x3b715228522152e786a2306a5a593dde65f17af0\",\"user_id\":\"jj14ixq\",\"sn\":\"102\"},{\"address\":\"0x3613e115244c87b367febac56855bda2e8688f49\",\"user_id\":\"jj104tr\",\"sn\":\"73\"},{\"address\":\"0x988d298cc7da03bfb147dac52fb7c7136ffc62ab\",\"user_id\":\"jihns3m\",\"sn\":\"48\"},{\"address\":\"0x9c88c3028d2e2bdacec86078aa3ae238350a43ec\",\"user_id\":\"jj104ts\",\"sn\":\"74\"},{\"address\":\"0xa17e40c3dc6ec97c08d471de7b2130ceb097bd8e\",\"user_id\":\"jj14ixy\",\"sn\":\"110\"},{\"address\":\"0xebd859f84202183583dd70ae3045772409092031\",\"user_id\":\"jihns3n\",\"sn\":\"49\"},{\"address\":\"0xf44cd34fb89c540517a86c2d5341468be9479df5\",\"user_id\":\"jhkspu9\",\"sn\":\"18\"},{\"address\":\"0xbe2808e4aef04a150760d51dee1f1fa45947195b\",\"user_id\":\"jj14ixm\",\"sn\":\"98\"},{\"address\":\"0x05de2077dc473afc5ad9be4fc9b32bf1e1d01ee8\",\"user_id\":\"ji70vcq\",\"sn\":\"33\"},{\"address\":\"0x9859810f74729faed5c34d3e2604b76ddd3e9a41\",\"user_id\":\"jj12b6f\",\"sn\":\"86\"},{\"address\":\"0xdf9705217be0cf3ceca7357d8a80d9b4c0f53a95\",\"user_id\":\"jh5f27o\",\"sn\":\"7\"},{\"address\":\"0x03fb1d29c32a55c5f4534b14972ad5ceb51c8d3d\",\"user_id\":\"jh5ilvw\",\"sn\":\"11\"},{\"address\":\"0xd1613784b7215c8804643acc539a68087822e8e3\",\"user_id\":\"jj104tq\",\"sn\":\"72\"},{\"address\":\"0x53f5709babb658acc89577b0d37c4a8c9e466226\",\"user_id\":\"jj104tu\",\"sn\":\"75\"},{\"address\":\"0x4379545926b9bcd8399ff0508cce40d6aacda91c\",\"user_id\":\"jh8496r\",\"sn\":\"12\"},{\"address\":\"0x940e60de2ce83d0a91143603edd6e4dc2948a27f\",\"user_id\":\"jj12b6c\",\"sn\":\"83\"},{\"address\":\"0x3e1620a6ee38ad328c4a56aff6d41a6f412cbc79\",\"user_id\":\"jj104th\",\"sn\":\"63\"},{\"address\":\"0x41488a2e1fb0e81825879712f759ffed76fe4a41\",\"user_id\":\"jj12b67\",\"sn\":\"80\"},{\"address\":\"0x5dce34737db0b43ca75aa74a83461de30bfb66db\",\"user_id\":\"jj12b63\",\"sn\":\"76\"},{\"address\":\"0xf196de16691a8bc1e24996e495d2ca6847ff9cae\",\"user_id\":\"jhmf79g\",\"sn\":\"26\"},{\"address\":\"0x5a2f0e07fb993ef369018916d8d0fe7cccbc50cf\",\"user_id\":\"jj14ixo\",\"sn\":\"100\"},{\"address\":\"0x43b69d43f082d1b564039c864b24ed5452372509\",\"user_id\":\"jj104to\",\"sn\":\"70\"},{\"address\":\"0x234297d179d60c9b63982eaa7e488b1fcf26185b\",\"user_id\":\"jihnsll\",\"sn\":\"57\"},{\"address\":\"0x385d34caca2de62b8b1f7f1f08a3d6ac3a051bb6\",\"user_id\":\"jhm7k9r\",\"sn\":\"21\"},{\"address\":\"0x2315531435c8f59f3d28646585728be50fc082ce\",\"user_id\":\"jhmf75u\",\"sn\":\"25\"},{\"address\":\"0xa5e776c6110142afc602cb7239338401279b7b67\",\"user_id\":\"jj12b6h\",\"sn\":\"88\"},{\"address\":\"0x55080c7c4afcc84fcf87db08afe8b4ff30b79566\",\"user_id\":\"jihnslg\",\"sn\":\"55\"},{\"address\":\"0xa84c28a61a76a7bef8a07d5073921b95e201b449\",\"user_id\":\"jihns3o\",\"sn\":\"50\"},{\"address\":\"0x6133c15fe929b03d6e077234aa1af66c567d024b\",\"user_id\":\"jj14ixu\",\"sn\":\"106\"},{\"address\":\"0x4d05c9bd9aef5f624aa937499bc2ecdeab551121\",\"user_id\":\"ji70vcw\",\"sn\":\"39\"},{\"address\":\"0x2839dd186ee92c4c95bfe466d73f6ee6167be176\",\"user_id\":\"jihnsl7\",\"sn\":\"51\"},{\"address\":\"0x3c0daed63ee3c1f2dfe336099e81f474ff5d1c05\",\"user_id\":\"jj104ti\",\"sn\":\"64\"},{\"address\":\"0xe3a21e8bbcdf74fd6c7c76ad0ba4241b943a2d2d\",\"user_id\":\"jj14ixv\",\"sn\":\"107\"},{\"address\":\"0xbcc545e884c5c3e04b2460922a8e1328e970d5a5\",\"user_id\":\"jj12b65\",\"sn\":\"78\"},{\"address\":\"0x40bfd863227b04b4ff1cd71f28c32f1ac5393c3b\",\"user_id\":\"ji70vct\",\"sn\":\"36\"},{\"address\":\"0xd0829c6180df3dafd8661d68c476b965321cd3f1\",\"user_id\":\"jihnslr\",\"sn\":\"60\"},{\"address\":\"0x69e049d806b366e7287b9cf50d9cb8735761b601\",\"user_id\":\"jj104tm\",\"sn\":\"68\"},{\"address\":\"0xffe9190e65391c897c729bbf281a1d6364c3caeb\",\"user_id\":\"jihns3k\",\"sn\":\"46\"},{\"address\":\"0x46193456cb458329e25053ba33eb288a3af210e0\",\"user_id\":\"jj14ixi\",\"sn\":\"94\"},{\"address\":\"0x63a375b7c85b897ebad7f3cc85a82cccf31649f3\",\"user_id\":\"jihnsld\",\"sn\":\"54\"},{\"address\":\"0x46890b65f2255545096b22124a7503efbc51dfea\",\"user_id\":\"jihns3h\",\"sn\":\"43\"},{\"address\":\"0x77c0cc61a796586376e0694559797d1948872b80\",\"user_id\":\"jj12b6e\",\"sn\":\"85\"},{\"address\":\"0xefac0053a796fc63f6ecda4e0c5306a52b626d6f\",\"user_id\":\"jh5he55\",\"sn\":\"9\"},{\"address\":\"0x8c32c1023b439ca949d1879f0e13d201dc832789\",\"user_id\":\"jj14ixh\",\"sn\":\"93\"},{\"address\":\"0x17bd716563a8e7d47aaeedbb100b2568c26adf6d\",\"user_id\":\"jj104tf\",\"sn\":\"61\"},{\"address\":\"0xca16c0f4edc7c4dabd5ee1bb0644ed99a4e5dc4e\",\"user_id\":\"jh4d7hn\",\"sn\":\"3\"},{\"address\":\"0x1be96ab6b2464b18d1b2d42f5158a7d9cdbc6bc5\",\"user_id\":\"jihns3e\",\"sn\":\"41\"},{\"address\":\"0x6a1b0ef703314ea9339cf904dd44525689efa945\",\"user_id\":\"jihnslp\",\"sn\":\"59\"},{\"address\":\"0x94a505ff5ecb7fe4e8fb9f3decde869a66b87296\",\"user_id\":\"ji6x6s3\",\"sn\":\"30\"},{\"address\":\"0x1b4fbb5c26cf2103368682fb77b6f6f7bd62c846\",\"user_id\":\"jj12b6g\",\"sn\":\"87\"},{\"address\":\"0xf576601613590e350f16058bb7e93f9f57594edd\",\"user_id\":\"ji70vcs\",\"sn\":\"35\"},{\"address\":\"0x3e2ea4f76374159050e6a91dc5572e2435c901ca\",\"user_id\":\"ji70vco\",\"sn\":\"31\"},{\"address\":\"0x34130aece0a161d0e26e7ca5a2e66d606af9b435\",\"user_id\":\"jj14ixl\",\"sn\":\"97\"},{\"address\":\"0x744e39d636af71d9d457af22d48f6356b0950e88\",\"user_id\":\"jhmf6pu\",\"sn\":\"22\"},{\"address\":\"0x202851451c1f7c08b9939444a0de27498c897ba2\",\"user_id\":\"jj104tg\",\"sn\":\"62\"},{\"address\":\"0x49705c48566f58d219666ea28c89fef8547cba56\",\"user_id\":\"jihns3l\",\"sn\":\"47\"},{\"address\":\"0xd2ba2c2c2e06c41d70bf221561a7703f74869c15\",\"user_id\":\"jihns3i\",\"sn\":\"44\"},{\"address\":\"0x682a66843a9ca955f18d82ac4d4547ea6d384df3\",\"user_id\":\"jh5g5ms\",\"sn\":\"8\"},{\"address\":\"0x85d522cdcda3781318e81eb4a27ea1d2e274a909\",\"user_id\":\"jj14ixn\",\"sn\":\"99\"},{\"address\":\"0x1f897f519d986269c96c69580f67cfd2f339810b\",\"user_id\":\"jj14ixp\",\"sn\":\"101\"},{\"address\":\"0xf016ed046965c6c41f3653803f46ecc35d729e78\",\"user_id\":\"jihnslk\",\"sn\":\"56\"},{\"address\":\"0x4e3a7260dda3ffe841be315481677c5f0b9fc27a\",\"user_id\":\"ji70vcu\",\"sn\":\"37\"},{\"address\":\"0x580a06108072e5da0eb663e59fd56ad5bce93078\",\"user_id\":\"jj14ixe\",\"sn\":\"92\"},{\"address\":\"0x1aaf1f447a59a3df30c5b59ab5ed377b867c690c\",\"user_id\":\"jhm7i8w\",\"sn\":\"19\"}]";

        JSONArray ja = JSONUtil.parseArray(str);
        System.out.println(ja.size());

        List<JSONObject> list = Util.getAll();


        List<Map<String,String>> lll = new ArrayList<>();

        for (JSONObject j:list){
            System.out.println(j.getStr("remark"));
            JSONObject remark = JSONUtil.parseObj(j.getStr("remark"));
            for (Object o:ja){
                JSONObject json = (JSONObject) o;
                if (json.getStr("user_id").equals(j.getStr("user_id"))){
                    remark.set("metamaskAdd",json.getStr("address"));
                    break;
                }
            }
            j.set("remark",remark.toStringPretty());

        }

        for (JSONObject j:list){
            System.out.println(j.getStr("remark"));

            Map<String,Object> pm = new HashMap<>();
            pm.put("user_id",j.getStr("user_id"));
            pm.put("remark",j.getJSONObject("remark").toStringPretty());

            String res = HttpUtil.post("local.adspower.net:50325/api/v1/user/update",JSONUtil.parse(pm).toStringPretty());
            System.out.println("修改结果为：" + res);
            Thread.sleep(1000);

        }

    }
}