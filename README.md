### Compile & run
**Console**
```
cd checksystem-cli
mvn clean install dependency:copy-dependencies
cd target/classes
java -classpath "../*;../dependency/*" ru/clevertec/checksystem/cli/Main
```

**Web**
```
cd checksystem-web
mvn clean install dependency:copy-dependencies
copy target/web-1.0.war "C:/Program Files/Apache Software Foundation/Tomcat 9.0/webapps/" (заменить путь папки Tomcat > если отличается)
в браузере http://localhost:8080/web-1.0/
```
---
### Console arguments
- **-mode=[generate/file-deserialize/pre-defined]**: этот аргумент всегда должен присутствовать, он отвечает за то что будет делать программа при её запуске. Доступные значения:
    - **generate:** сгенерировать чек из аргументов. При этом значении аргумента становятся доступны аргументы:
        - **-id**, id чека 
        - **-name**, название чека
        - **-description**, описание чека
        - **-address**, аддресс чека
        - **-cashier**, кассир чека
        - **-phoneNumber**, телефон чека
        - **-date**, дата чека
        - **-ci=[productId:quantity]**, это команда добавляет продукт с указаным Id и количством в чек, этот аргумент может повторяться 
        - **-d-check**, скидка на весь чек, этот аргумент может повторяться
        - **-d-item**, скидка на определенный продукт в чеке, этот аргумент может повторяться
    - **file-deserialize=[json/xml]:** загрузить чеки из файла. При этом значении должны присуствывать аргументы:
        - **-file-deserialize-path=[file_path]**, путь к файлу, который будет читатьс.
        - **-file-deserialize-format=[json/xml]**, формат файла, который будет читаться.
    -  **pre-defined:** загрузить предопределенные чеки из памяти
___
- **-file-serialize=[true/false]**: этот агрумент говорит что все чеки которые были загружены или сгенерированы - будут записаны в файл, например другого формата, json->xml или наоборот.
При этом аргументе должны присуствывать аргументы:
    - **-file-serialize-path=[file_path]:** путь к файлу для сохранения
	- **-file-serialize-format=[json/xml]:** формат файла для сохранения
---
- **-file-print=[true/false]**: этот аргумент говорит что все чеки которые были загружены или сгенерированы - будут записаны в файл в формате простого чека, например для покупателя (на эти чеки можно только смотреть, загружать обратно в программу нельзя). При этом аргументе должны присутствовать аргументы:
    - **-file-print-path=[file_path]=:** путь к файлу для сохранения
    - **-file-print-format=[text/html]=:** формат файла для сохранения
---
- **-filter-id=[id1,id2,id3]**: фильтрация чеков по Id, неважно откуда были загружены чеки, из файла или памяти
		этот аргумент доступен при **-mode=[file-deserialize/pre-defined]**
---

### Примеры комманд:
**Сгенерировать чек:**
```
-mode=generate -id=1 -name="Magazin 000" -description="Super magazin" -address="ul. Pupkina 1" -cashier="V. Pupkin" -phoneNumber="000000000000" -date="01.01.2007" -ci=1:1 -ci=2:1 -ci=3:1 -ci=2:5 -ci=5:1 -ci=6:8 -ci=7:15 -ci=8:67 -ci=9:3 -ci=10:1 -ci=11:1 -ci=12:1 -ci=13:4 -ci=14:2 d-check=1 d-item=6:5
```
	
**Сгенерировать чек и сохранить его в файл в формате JSON:**
```
-mode=generate -file-serialize=true -file-serialize-format=json -file-serialize-path="C:/test/checks.json" -id=1 -name="Magazin 000" -description="Super magazin" -address="ul. Pupkina 1" -cashier="V. Pupkin" -phoneNumber="000000000000" -date="01.01.2007" -ci=1:1 -ci=2:1 -ci=3:1 -ci=2:5 -ci=5:1 -ci=6:8 -ci=7:15 -ci=8:67 -ci=9:3 -ci=10:1 -ci=11:1 -ci=12:1 -ci=13:4 -ci=14:2 d-check=1 d-item=6:5
```

**Загрузить чеки из памяти:**
```
-mode=pre-defined
```

**Загрузить чеки и сохранить их в файл в формате XML:**
```
-mode=pre-defined -file-serialize=true -file-serialize-format=xml -file-serialize-path="C:/test/checks.xml"
```

**Загрузить чеки из памяти и напечатать их в файл в формате Text:**
```
-mode=pre-defined -file-print=true -file-print-format=text -file-print-path="C:/test/checks.txt"
```

**Загрузить чеки из памяти, отфильтровать по Id, и напечатать их в файл в формате Text:**
```
-mode=pre-defined -filter-id="1,2,3" -file-print=true -file-print-format=text -file-print-path="C:/test/checks.txt"
```

**Загрузить чеки из JSON файла:**
```
-mode=file-deserialize -file-deserialize-path="C:/test/checks.json" -file-deserialize-format=json
```
		
**Загрузить чеки из JSON файла и сохранить их в файл в формате XML:**
```
-mode=file-deserialize -file-deserialize-path="C:/test/checks.json" -file-deserialize-format=json -file-serialize=true -file-serialize-format=xml -file-serialize-path="C:/test/checks_converted.xml"
```

**Загрузить чеки из XML и напечатать их в файл в формате HTML:**
```
-mode=file-deserialize -file-deserialize-path="C:/test/checks.xml" -file-deserialize-format=xml -file-serialize=true -file-print-format=text -file-print-path="C:/test/checks.html"
```
---
### Web
[localhost:8080](http://localhost:8080/)
> В web версии можно загрузить чеки из файла, посмотреть на них, конвертировать в другой формат и скачать.
 
#### Примеры:
```
http://localhost:8080/checks/type=memory
```
```
http://localhost:8080/checks/type=memory?id=1&id=2
```
```
http://localhost:8080/checks/type=session
// session - значит из файла, файл хранится в сессии с тех пор когда был загружен и до закрытия браузера
```
```
http://localhost:8080/checks/type=session?id=1&id=2
```
	