__<p align="center"> Terminal Network (Project PO) </p>__
___
The goal of this project is to develop an application to manage a network of communication terminals, called prr. Generically, the program allows the registration, management and consultation of clients, terminals and communications.

[Project Statement](docs/statement.pdf) | [Evaluation Methods](docs/evaluation.pdf)
___
### Requirements:
Java 14+
___
### Use:

1. Clone the repo
```
git clone https://github.com/Gocho1234/TerminalNetwork-PO.git
```

2. Set the classpath
```
cd TerminalNetwork-PO
export CLASSPATH=./prr-app/prr-app.jar:./prr-core/prr-core.jar:./po-uilib/po-uilib.jar:.
```

3. Run project manually
```
make
```

4. Run tests on the project
```
./run_tests.sh
```

In case you get this error `bash: ./run_tests.sh: Permission denied` run
```
chmod +x run_tests.sh
```
