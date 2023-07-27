# LivePoll

[Javadoc link](https://m5c.github.io/LivePoll/)

## Usage

```bash
git clone https://github.com/m5c/LivePoll
cd LivePoll
mvn clean package
java -jar target/LivePoll.jar
```

 * Open browser at  [http://127.0.0.1:8361](http://127.0.0.1:8361)
 * Use phone (must be in same LAN) to scan QR-Code
 * Press sapcebar to display results

 > You can program series of questions using HTTP calls to the REST backend with a bash script. See [`poll.sh`](poll.sh) for a sample.

## Author / Pull Requests

 * Maximilian Schiedermeier
 * [McGill University](https://www.cs.mcgill.ca/~mschie3/)
 * Github: `m5c`
