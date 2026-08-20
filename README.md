> ⚠️ **This project is officially retired.** `sdkman-disco-integration` no longer runs. Java version data now flows directly from the [Foojay DISCO API](https://github.com/foojayio/discoapi) into [`sdkman-state`](https://github.com/sdkman/sdkman-state), so this scraping/MongoDB-push pipeline is no longer needed. This repository is kept for historical reference only.
>
> Thanks to everyone who contributed over the years! 💚

---

# sdkman-disco-migration

The source code in this repository is used by GitHub actions to find the latest version of a given Java vendor and post it to [SDKMAN!](https://github.com/sdkman/).

All versions are fetched from [Foojay´s Disco API](https://github.com/foojayio/discoapi)

List of supported vendors:
* Alibaba Dragonwell
* Amazon Corretto
* Azul Zulu
* BellSoft Liberica
* BellSoft Liberica Native Image Kit
* IBM Semeru
* Gluon GraalVM
* GraalVM
* Huawei BiSheng
* Mandrel
* Microsoft OpenJDK
* Oracle
* OpenJDK
* SapMachine
* Eclipse Temurin
* Tencent Kona
* Trava OpenJDK
* JetBrains Runtime
