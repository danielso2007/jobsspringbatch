# foldermonitor

Projeto de teste com spring batch que fica monitorando arquivo de uma pasta.

É preciso definir uma pasta para o monitoramento. No arquivo application.properties, configurar `foldermonitor.local`. Exemplo: `foldermonitor.local=/home/daniel/work`.

Todo arquivo inserido na pasta `/home/daniel/work`, os dados serão adicionados em banco.

### Arquivo

O arquivo `visitors.csv` de exemplo está na pasta `resources`.
