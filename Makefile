PRR_CORE_PATH=./prr-core
PRR_APP_PATH=./prr-app
PO_UILIB_PATH=./po-uilib
CLASSPATH=$(shell pwd)/po-uilib/po-uilib.jar:$(shell pwd)/prr-app/prr-app.jar:$(shell pwd)/prr-core/prr-core.jar

all::
	$(MAKE) $(MFLAGS) -C $(PO_UILIB_PATH)
	$(MAKE) $(MFLAGS) -C $(PRR_CORE_PATH)
	$(MAKE) $(MFLAGS) -C $(PRR_APP_PATH)
	CLASSPATH=$(CLASSPATH) java prr.app.App

clean:
	$(MAKE) $(MFLAGS) -C $(PO_UILIB_PATH) clean
	$(MAKE) $(MFLAGS) -C $(PRR_CORE_PATH) clean
	$(MAKE) $(MFLAGS) -C $(PRR_APP_PATH) clean

tests::
	$(MAKE) $(MFLAGS) -C $(PO_UILIB_PATH)
	$(MAKE) $(MFLAGS) -C $(PRR_CORE_PATH)
	$(MAKE) $(MFLAGS) -C $(PRR_APP_PATH)
