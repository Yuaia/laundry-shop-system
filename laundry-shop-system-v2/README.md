# Laundry Shop System (v2)

## 功能簡介
- 顧客新增（透過 API）
- 資料寫入 MySQL（DBeaver 確認）
- application.properties 中已設定範例連線（修改密碼）

## 啟動方式
```bash
mvn spring-boot:run
```

或在 Windows 使用：

```bash
mvnw.cmd spring-boot:run
```

## API 測試
POST /api/customers

```json
{
  "name": "小明",
  "phone": "0912345678",
  "member": true
}
```
