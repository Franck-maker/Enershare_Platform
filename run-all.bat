@echo off
echo ==================================================
echo 🚀 Starting EnerShare Microservices Architecture
echo ==================================================

echo 1. Starting Community Service (Port 8081)...
start "Community Service" java -jar community/target/community-1.0.0-SNAPSHOT.jar

echo 2. Starting Metering Service (Port 8082)...
start "Metering Service" java -jar metering/target/metering-1.0.0-SNAPSHOT-exec.jar

echo 3. Starting Trading Service (Port 8083)...
start "Trading Service" java -jar trading/target/trading-1.0.0-SNAPSHOT.jar

echo 4. Starting Wallet Service (Port 8084)...
start "Wallet Service" java -jar wallet/target/wallet-1.0.0-SNAPSHOT.jar

echo ==================================================
echo ✅ All services are launching in separate windows.
echo ⚠️ Ensure Docker is running first!
echo ==================================================
pause