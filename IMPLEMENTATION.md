# Iron Block Elevator プラグイン実装詳細

## プロジェクト構成

```
Iron-Block-Elevator/
├── build.gradle              # Gradle ビルド設定
├── settings.gradle           # Gradle プロジェクト設定
├── gradle.properties         # Gradle プロパティ
├── gradlew                   # Gradle Wrapper (Unix/Linux/Mac)
├── gradlew.bat              # Gradle Wrapper (Windows)
├── gradle/wrapper/          # Gradle Wrapper ファイル
├── .gitignore               # Git 除外設定
├── README.md                # プロジェクトドキュメント
└── src/main/
    ├── java/com/kubota6646/ironblockelevator/
    │   ├── IronBlockElevator.java    # メインプラグインクラス
    │   ├── ElevatorListener.java     # イベントリスナー
    │   ├── ElevatorConfig.java       # 設定管理クラス
    │   └── ElevatorCommand.java      # コマンド処理
    └── resources/
        ├── plugin.yml        # プラグイン定義
        └── config.yml       # デフォルト設定
```

## 実装の詳細

### 1. IronBlockElevator.java (メインクラス)
- JavaPlugin を拡張したメインのプラグインクラス
- プラグインの初期化、有効化、無効化を管理
- 設定ファイルの読み込み
- イベントリスナーとコマンドの登録

### 2. ElevatorListener.java (イベントリスナー)
- PlayerMoveEvent を監視
- プレイヤーが鉄ブロックの上にいるかをチェック
- ジャンプ時: 上方向の次の鉄ブロックを検索して移動
- スニーク時: 下方向の次の鉄ブロックを検索して移動
- 安全性チェック: 移動先に2ブロック分の空間があることを確認

### 3. ElevatorConfig.java (設定管理)
- config.yml の設定値を読み込み
- 速度、最大高度、有効/無効などの設定を管理
- デバッグモードのサポート

### 4. ElevatorCommand.java (コマンド処理)
- `/ironblockelevator` (または `/ibe`) コマンドの処理
- プラグイン情報の表示
- `/ibe reload` で設定のリロード機能

## 動作原理

1. **上昇メカニズム**
   - プレイヤーが鉄ブロックの上でジャンプ
   - 上方向に次の鉄ブロックを検索
   - 見つかった場合、設定された速度で上昇させる

2. **下降メカニズム**
   - プレイヤーが鉄ブロックの上でスニーク
   - 下方向に次の鉄ブロックを検索
   - 見つかった場合、設定された速度で下降させる

3. **安全性**
   - 移動先の鉄ブロックの上に2ブロック分の空間が必要
   - 最大高度制限を設定可能
   - プレイヤーが窒息しないように配慮

## ビルド要件

- **Java**: 17以上
- **Gradle**: 8.5（Wrapper により自動取得）
- **IntelliJ IDEA**: 2025.3.1 推奨
- **Minecraft API**: Paper/Spigot API 1.19.4-R0.1-SNAPSHOT

## IntelliJ IDEA での開発

1. IntelliJ IDEA でプロジェクトを開く
2. Gradle が自動的に依存関係をダウンロード
3. コードの編集、デバッグが可能
4. Gradle タスクから直接ビルド実行

## 設定オプション

### config.yml
```yaml
enabled: true              # プラグインの有効/無効
upward-speed: 0.5         # 上昇速度
downward-speed: 0.5       # 下降速度
max-height: 256           # 最大高度
debug: false              # デバッグモード
```

## 今後の拡張可能性

1. エレベーターの視覚効果（パーティクル）
2. 音響効果
3. 他のブロックタイプのサポート
4. 権限システムの追加
5. エレベーター使用時のクールダウン
6. 複数プレイヤーの同時使用対応
