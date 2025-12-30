# クイックスタートガイド

## すぐに始める

### 1. IntelliJ IDEA 2025.3.1 でプロジェクトを開く

```
File → Open → Iron-Block-Elevator フォルダを選択
```

### 2. Gradle を同期

IntelliJ が自動的に Gradle プロジェクトを検出し、依存関係をダウンロードします。

### 3. ビルド

#### 方法 A: IntelliJ IDEA
- 右側の「Gradle」ツールウィンドウを開く
- `IronBlockElevator` → `Tasks` → `build` → `build` をダブルクリック

#### 方法 B: コマンドライン
```bash
./gradlew build
```

### 4. プラグインファイルを取得

ビルド完了後、以下の場所に JAR ファイルが生成されます：
```
build/libs/IronBlockElevator-1.0.0.jar
```

### 5. サーバーにインストール

1. Minecraft 1.19.4 Paper/Spigot サーバーの `plugins` フォルダに JAR をコピー
2. サーバーを起動
3. コンソールで "IronBlockElevator has been enabled!" を確認

### 6. エレベーターを作る

1. 垂直に鉄ブロックを配置（例: Y=64, Y=74, Y=84）
2. 各鉄ブロックの上に2ブロック分の空間を確保
3. 鉄ブロックの上に立つ
4. ジャンプ (Space) で上昇
5. スニーク (Shift) で下降

## コマンド

```
/ibe          - プラグイン情報
/ibe reload   - 設定リロード
```

## 設定ファイル

`plugins/IronBlockElevator/config.yml`
```yaml
enabled: true           # 有効/無効
upward-speed: 0.5      # 上昇速度
downward-speed: 0.5    # 下降速度
max-height: 256        # 最大高度
debug: false           # デバッグモード
```

## トラブルシューティング

### ビルドエラー
- Java 17+ がインストールされているか確認
- `./gradlew clean build` を実行

### プラグインが動かない
- Paper/Spigot 1.19.4 を使用しているか確認
- config.yml で `enabled: true` になっているか確認

### エレベーターが動かない
- 鉄ブロックの上に2ブロック分の空間があるか確認
- ジャンプ/スニークを正しく実行しているか確認

## サポート

詳細は以下のドキュメントを参照：
- **README.md**: 基本情報
- **IMPLEMENTATION.md**: 実装詳細
- **TESTING.md**: テスト方法
- **PROJECT_SUMMARY.md**: プロジェクト概要
