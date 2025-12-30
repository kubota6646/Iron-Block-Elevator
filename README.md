# Iron-Block-Elevator

Minecraft Java版 1.19.4 対応の鉄ブロックエレベータープラグイン

## 概要

このプラグインは、鉄ブロックを使ったエレベーター機能を実装します。プレイヤーは鉄ブロックの上でジャンプして上昇し、スニークして下降することができます。

## 機能

- 鉄ブロックの上でジャンプすると、上方向にある次の鉄ブロックまで移動
- 鉄ブロックの上でスニークすると、下方向にある次の鉄ブロックまで移動
- 設定ファイルで速度やその他のオプションをカスタマイズ可能
- リロードコマンドでサーバーを再起動せずに設定を再読み込み

## ビルド方法

このプラグインは IntelliJ IDEA 2025.3.1 の Gradle でビルドできます。

### IntelliJ IDEA でのビルド手順

1. IntelliJ IDEA でプロジェクトを開く
2. Gradle ツールウィンドウを開く（View -> Tool Windows -> Gradle）
3. Tasks -> build -> build をダブルクリック
4. ビルドが完了すると、`build/libs/` フォルダに JAR ファイルが生成されます

### コマンドラインでのビルド

```bash
./gradlew build
```

ビルドされたプラグインは `build/libs/IronBlockElevator-0.0.1.jar` に生成されます。

## インストール

1. ビルドされた JAR ファイルをサーバーの `plugins` フォルダにコピー
2. サーバーを起動または再起動
3. プラグインが正常に読み込まれたことを確認

## 使い方

### エレベーターの構築

1. 垂直方向に鉄ブロックを配置（間隔は自由）
2. 各鉄ブロックの上に2ブロック分の空間を確保
3. 鉄ブロックの上に立ってジャンプまたはスニークで移動

### コマンド

- `/ironblockelevator` または `/ibe` - プラグイン情報を表示
- `/ironblockelevator reload` - 設定ファイルを再読み込み（権限: `ironblockelevator.reload`）

## 設定

`plugins/IronBlockElevator/config.yml` で設定をカスタマイズできます：

```yaml
# プラグインの有効/無効
enabled: true

# 上昇速度
upward-speed: 0.5

# 下降速度
downward-speed: 0.5

# 最大高度
max-height: 256

# デバッグメッセージ
debug: false
```

## 技術仕様

- Minecraft バージョン: 1.19.4
- API: Paper/Spigot API 1.19.4-R0.1-SNAPSHOT
- Java バージョン: 17
- ビルドツール: Gradle 8.5

## ライセンス

このプロジェクトは MIT ライセンスの下で公開されています。