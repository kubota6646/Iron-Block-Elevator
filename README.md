# Iron-Block-Elevator

Minecraft Java版 1.19.4 対応のブロックエレベータープラグイン

[![Version](https://img.shields.io/badge/version-0.0.1-blue.svg)](https://github.com/kubota6646/Iron-Block-Elevator)
[![Minecraft](https://img.shields.io/badge/minecraft-1.19.4-green.svg)](https://www.spigotmc.org/)
[![License](https://img.shields.io/badge/license-MIT-yellow.svg)](LICENSE)

## 📖 概要

このプラグインは、設定可能なブロックを使った垂直移動システムを提供します。プレイヤーは指定されたブロックの上でジャンプして上昇し、スニークして下降することができます。

### 主な特徴

- ✨ **カスタマイズ可能なブロック**: 鉄ブロック以外にも、金ブロック、ダイヤモンドブロックなど、任意のブロックをエレベーターとして設定可能
- 🔒 **GriefPrevention 連携**: 土地保護システムと完全連携
- 🇯🇵 **完全日本語対応**: すべてのメッセージが日本語で編集可能
- ⚡ **高速・軽量**: イベント駆動型の効率的な実装
- 🔧 **ホットリロード**: サーバー再起動なしで設定変更が可能

## 🎮 使い方

### エレベーターの作り方

1. **ブロックを縦に配置**
   ```
   高さ Y=80: [エレベーターブロック] ← 到着地点
                └─ 2ブロック分の空間
   
   高さ Y=70: [エレベーターブロック] ← 中間地点
                └─ 2ブロック分の空間
   
   高さ Y=60: [エレベーターブロック] ← 出発地点
                └─ 2ブロック分の空間
   ```

2. **エレベーターを使用**
   - **上昇**: エレベーターブロックの上に立ち、**ジャンプ（スペースキー）**
   - **下降**: エレベーターブロックの上に立ち、**スニーク（Shiftキー）**

### 重要な注意点

- 各エレベーターブロックの上には **2ブロック分の空間** が必要です
- 同じ X・Z 座標の垂直線上にブロックを配置してください
- ブロック間の距離は自由に設定できます

## 📋 コマンド一覧

| コマンド | エイリアス | 説明 | 権限 |
|---------|----------|------|------|
| `/ironblockelevator` | `/ibe` | プラグイン情報を表示 | なし |
| `/ironblockelevator reload` | `/ibe reload` | 設定ファイルとメッセージを再読み込み | `ironblockelevator.reload` |

### コマンドの使用例

```bash
# プラグイン情報を確認
/ibe

# 設定をリロード（権限が必要）
/ibe reload
```

## ⚙️ 設定

### config.yml

`plugins/IronBlockElevator/config.yml` で動作をカスタマイズできます：

```yaml
# プラグイン機能の有効/無効
enabled: true

# エレベーターとして使用するブロックの種類
# 例: IRON_BLOCK, GOLD_BLOCK, DIAMOND_BLOCK, EMERALD_BLOCK, QUARTZ_BLOCK
# 利用可能なブロック一覧: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html
elevator-block: IRON_BLOCK

# 上昇時の垂直速度（エレベーターブロックの上でジャンプした時）
upward-speed: 0.5

# 下降時の垂直速度（エレベーターブロックの上でスニークした時）
downward-speed: 0.5

# エレベーターが移動できる最大高度
max-height: 256

# デバッグメッセージの有効化
debug: false
```

### message.yml

`plugins/IronBlockElevator/message.yml` ですべてのメッセージをカスタマイズできます：

- プラグイン起動/停止メッセージ
- コマンド関連メッセージ
- デバッグメッセージ
- エラーメッセージ

メッセージはカラーコード（`&e`, `&a`, `&c` など）に対応しています。

### エレベーターブロックの設定例

```yaml
# VIP エリア用（金ブロック）
elevator-block: GOLD_BLOCK

# 特別ゾーン用（ダイヤモンドブロック）
elevator-block: DIAMOND_BLOCK

# スポーン地点用（エメラルドブロック）
elevator-block: EMERALD_BLOCK

# モダンな建築用（クォーツブロック）
elevator-block: QUARTZ_BLOCK
```

## 🔒 権限

| 権限 | 説明 | デフォルト |
|-----|------|-----------|
| `ironblockelevator.reload` | 設定のリロードを許可 | op |

## 🛡️ GriefPrevention 連携

GriefPrevention がインストールされている場合、土地保護システムと連携します：

| 状況 | 動作 |
|-----|------|
| 保護されていない土地 | すべてのプレイヤーが使用可能 |
| 自分の土地 | 土地の所有者は使用可能 |
| 他人の保護された土地 | 使用不可 |
| accesstrust を付与された土地 | 使用可能 |

### accesstrust の付与方法

土地の所有者は以下のコマンドで他のプレイヤーにエレベーターの使用権限を付与できます：

```bash
/accesstrust <プレイヤー名>
```

## 🔧 ビルド方法

このプラグインは IntelliJ IDEA 2025.3.1 の Gradle でビルドできます。

### 必要な環境

- Java 17 以上
- Gradle 8.5（Wrapper に含まれています）
- IntelliJ IDEA 2025.3.1（推奨）

### IntelliJ IDEA でのビルド

1. IntelliJ IDEA でプロジェクトを開く
2. Gradle ツールウィンドウを開く（View → Tool Windows → Gradle）
3. Tasks → build → build をダブルクリック
4. ビルドが完了すると、`build/libs/IronBlockElevator-0.0.1.jar` が生成されます

### コマンドラインでのビルド

```bash
# Unix/Linux/macOS
./gradlew build

# Windows
gradlew.bat build
```

ビルドされたプラグインは `build/libs/IronBlockElevator-0.0.1.jar` に生成されます。

## 📦 インストール

1. **プラグインの配置**
   - ビルドされた `IronBlockElevator-0.0.1.jar` をサーバーの `plugins` フォルダにコピー

2. **サーバーの起動**
   - サーバーを起動または再起動

3. **動作確認**
   - コンソールに "IronBlockElevator が有効になりました！" と表示されることを確認
   - `/ibe` コマンドでプラグイン情報が表示されることを確認

4. **設定のカスタマイズ（オプション）**
   - `plugins/IronBlockElevator/config.yml` を編集
   - `plugins/IronBlockElevator/message.yml` を編集（メッセージをカスタマイズする場合）
   - `/ibe reload` で設定を反映

## 🔍 トラブルシューティング

### エレベーターが動かない場合

1. **プラグインが有効か確認**
   ```bash
   /plugins
   ```
   IronBlockElevator が緑色で表示されているか確認

2. **config.yml の設定を確認**
   - `enabled: true` になっているか
   - `elevator-block` に正しいブロック名が設定されているか

3. **エレベーターの構造を確認**
   - エレベーターブロックの上に2ブロック分の空間があるか
   - 同じ X・Z 座標に複数のブロックが配置されているか

4. **GriefPrevention の保護を確認**
   - 保護された土地の場合、権限があるか確認
   - `/trust` コマンドで権限を確認

5. **デバッグモードを有効化**
   ```yaml
   debug: true
   ```
   設定後、`/ibe reload` を実行し、コンソールログを確認

### 無効なブロックタイプエラーが出る場合

config.yml の `elevator-block` に指定した値が正しいか確認してください。利用可能なブロック名は [Spigot Material ドキュメント](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html) を参照してください。

## 📊 技術仕様

| 項目 | 詳細 |
|-----|------|
| Minecraft バージョン | 1.19.4 |
| API | Paper/Spigot API 1.19.4-R0.1-SNAPSHOT |
| Java バージョン | 17 |
| ビルドツール | Gradle 8.5 |
| 連携プラグイン | GriefPrevention（オプション） |

## 📝 更新履歴

### v0.0.1（初回リリース）
- ✨ 基本的なエレベーター機能の実装
- 🔧 設定可能なブロックタイプ
- 🔒 GriefPrevention 連携
- 🇯🇵 完全日本語対応
- 📋 メッセージのカスタマイズ機能

## 🤝 貢献

バグ報告や機能要望は [Issues](https://github.com/kubota6646/Iron-Block-Elevator/issues) でお願いします。

## 📄 ライセンス

このプロジェクトは [MIT ライセンス](LICENSE) の下で公開されています。

## 👤 作者

kubota6646

---

**楽しいマインクラフトライフを！** 🎮✨