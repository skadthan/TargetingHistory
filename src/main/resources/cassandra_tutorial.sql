CREATE KEYSPACE ASHU WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};
schema_keyspaces
traffickeyspace
CREATE TABLE keyvalue(id UUID PRIMARY KEY, key text , value int );]
CREATE TABLE customer_record_disposition(ssoid uuid PRIMARY KEY, offer_name text, placement text, disposition_name text, disposition_date timestamp);
INSERT INTO keyvalue (id, key, value ) VALUES ( uuid(),'chris', 5);
INSERT INTO keyvalue (id, key, value ) VALUES ( uuid(),'luke', 10);
INSERT INTO keyvalue (id, key, value ) VALUES ( uuid(),'patrick', 15);
INSERT INTO keyvalue (id, key, value ) VALUES ( uuid(),'haddad', 20);

insert into customer_record_disposition(ssoid,disposition_date,disposition_name,offer_name,placement) values (uuid(),toUnixTimestamp(now()),'viewed','loan_offer_01','CustomerMessage');
insert into customer_record_disposition(ssoid,disposition_date,disposition_name,offer_name,placement) values (uuid(),toUnixTimestamp(now()),'accepted','loan_offer_01','CustomerMessage');
insert into customer_record_disposition(ssoid,disposition_date,disposition_name,offer_name,placement) values (uuid(),toUnixTimestamp(now()),'viewed','loan_offer_02','CustomerMessage');
insert into customer_record_disposition(ssoid,disposition_date,disposition_name,offer_name,placement) values (uuid(),toUnixTimestamp(now()),'viewed','loan_offer_02','CustomerMessage');
insert into customer_record_disposition(ssoid,disposition_date,disposition_name,offer_name,placement) values (uuid(),toUnixTimestamp(now()),'clicked','loan_offer_01','CustomerMessage');


create table customer_targetting_history(ssoid uuid, offer_name text, disposition_name text,
 days_since_last_cmnctn_viewed int, days_since_last_cmnctn_accepted int, 
 days_since_last_cmnctn_converted int, days_since_last_cmnctn_denied int, days_since_last_cmnctn_liked int, days_since_last_cmnctn_clicked int,
PRIMARY KEY (ssoid, offer_name, disposition_name)
);

create table tbl_set_list_map(rowkey ascii, setfield set<text>, listfield list<text>, mapfield map<text, text>, primary key (rowkey));
insert into tbl_set_list_map(rowkey,setfield,listfield,mapfield) values ('1', {'Apple','Lemon','Orange'},['Ashwath','Harika','Suresh'],{'Ashwath':'Apple','Harika':'Orange','Suresh':'Lemon'})
insert into tbl_set_list_map(rowkey,setfield,listfield,mapfield) values ('2', {'Apple2','Lemon2','Orange2'},['Ashwath2','Harika2','Suresh2'],{'Ashwath2':'Apple2','Ashwath2':'Apple2','Harika2':'Orange2','Suresh2':'Lemon2'})


CREATE OR REPLACE FUNCTION state_group_and_max(state map<text, int>, type text, amount int)
  CALLED ON NULL INPUT
  RETURNS map<text, int>
  LANGUAGE java AS '
    Integer val = (Integer) state.get(type);
    if (val == null) val = amount; else val = Math.max(val, amount);
    state.put(type, val);
    return state;
  ' ;

CREATE OR REPLACE AGGREGATE state_group_and_max(text, int) 
  SFUNC state_group_and_max
  STYPE map<text, int> 
  INITCOND {};


------------------------------------------------------------------------------------------------------------------------------------------------------
Relational:

The following is the Data Definition Language (DDL) of the two tables:
CREATE TABLE stock_symbol (
symbol varchar PRIMARY KEY,
description varchar,
exchange varchar
);
CREATE TABLE stock_ticker (
symbol varchar references stock_symbol(symbol),
tick_date varchar,
open decimal,
high decimal,
low decimal,
close decimal,
volume bigint,
PRIMARY KEY (symbol, tick_date)
);

Consider the following three cases: first, we want to list out all stocks and their
description in all exchanges. The SQL query for this is very simple:
// Query A
SELECT symbol, description, exchange
FROM stock_symbol;
Second, if we want to know all the daily close prices and descriptions of the stocks
listed in the NASDAQ exchange, we can write a SQL query as:
// Query B
SELECT stock_symbol.symbol, stock_symbol.description,
stock_ticker.tick_date, stock_ticker.close
FROM stock_symbol, stock_ticker
WHERE stock_symbol.symbol = stock_ticker.symbol
AND stock_symbol.exchange = ''NASDAQ'';

Furthermore, if we want to know all the day close prices and descriptions of the stocks
listed in the NASDAQ exchange on April 24, 2014, we can use the following SQL query:

// Query C
SELECT stock_symbol.symbol, stock_symbol.description,
stock_ticker.tick_date, stock_ticker.open,
stock_ticker.high, stock_ticker.low, stock_ticker_close,
stock_ticker.volume
FROM stock_symbol, stock_ticker
WHERE stock_symbol.symbol = stock_ticker.symbol
AND stock_symbol.exchange = ''NASDAQ''
AND stock_ticker.tick_date = ''2014-04-24'';
By virtue of the relational data model, we can simply write different SQL queries to
return different results with no changes to the underlying data model at all.

Cassandra version:

Now let us turn to Cassandra. The DDL statements in the last section can be slightly
modified to create column families, or tables, in Cassandra, which are as follows:
CREATE TABLE stock_symbol (
symbol varchar PRIMARY KEY,
description varchar,
exchange varchar
);
CREATE TABLE stock_ticker (
symbol varchar,
tick_date varchar,
open decimal,
high decimal,
low decimal,
close decimal,
volume bigint,
PRIMARY KEY (symbol, tick_date)
);
They seem to be correct at first sight.
As for Query A, we can query the Cassandra stock_symbol table exactly the
same way:
// Query A
SELECT symbol, description, exchange
FROM stock_symbol;

INSERT INTO stock_symbol (symbol, description, exchange ) VALUES ( 'COF','Capital One Financials', 'NASDAQ');
INSERT INTO stock_symbol (symbol, description, exchange ) VALUES ( 'APPLE','APPLE Financials', 'NASDAQ');
INSERT INTO stock_symbol (symbol, description, exchange ) VALUES ( 'ASHU','ASHU Financials', 'SADAHAQ');

INSERT INTO stock_ticker (symbol, tick_date, open, high, low, close, volume ) VALUES ( 'ASHU','02-02-2018', 230.01,660.00,320.00,758.00,200291);
INSERT INTO stock_ticker (symbol, tick_date, open, high, low, close, volume ) VALUES ( 'COF','02-02-2018', 440.01,360.00,720.00,858.00,2900292);
INSERT INTO stock_ticker (symbol, tick_date, open, high, low, close, volume ) VALUES ( 'APPLE','02-02-2018', 330.01,260.00,620.00,958.00,200295);

INSERT INTO stock_ticker (symbol, tick_date, open, high, low, close, volume ) VALUES ( 'APPLE','02-03-2018', 930.01,460.00,420.00,758.00,8900252);
INSERT INTO stock_ticker (symbol, tick_date, open, high, low, close, volume ) VALUES ( 'ASHU','02-03-2018', 830.01,560.00,620.00,658.00,640239);
INSERT INTO stock_ticker (symbol, tick_date, open, high, low, close, volume ) VALUES ( 'COF','02-03-2018', 2730.01,160.00,520.00,958.00,4500592);

create colun-family for qyery B:

CREATE TABLE stock_ticker_by_exchange (
exchange varchar,
symbol varchar,
description varchar,
tick_date varchar,
close decimal,
PRIMARY KEY (exchange, symbol, tick_date)
);

A suitable column family for Query C should resemble the following code:
// Query C
CREATE TABLE stock_ticker_by_exchange_date (
exchange varchar,
symbol varchar,
description varchar,
tick_date varchar,
close decimal,
PRIMARY KEY ((exchange, tick_date), symbol)
);

create table test_table(rowkey ascii, counterfield counter, primary key (rowkey));


------------------------------------------------------------------------------------------------------------------------------------------------------


CREATE FUNCTION maxI(current int, candidate int)
CALLED ON NULL INPUT
RETURNS int LANGUAGE java AS
'if (current == null) return candidate; else return Math.max(current, candidate);' ;

CREATE FUNCTION minI(current int, candidate int)
CALLED ON NULL INPUT
RETURNS int LANGUAGE java AS
'if (current == null) return candidate; else return Math.min(current, candidate);' ;

CREATE FUNCTION avgI(current int, candidate int)
CALLED ON NULL INPUT
RETURNS int LANGUAGE java AS
'if (current == null) return candidate; else return Math.avg(current, candidate);' ;

CREATE AGGREGATE maxAgg(int)
SFUNC maxI
STYPE int
INITCOND null;

CREATE AGGREGATE minAgg(int)
SFUNC minI
STYPE int
INITCOND null;

CREATE AGGREGATE minAgg(int)
SFUNC avgI
STYPE int
INITCOND null;



CREATE OR REPLACE FUNCTION state_group_and_count( state map<text, int>, type text )
CALLED ON NULL INPUT
RETURNS map<text, int>
LANGUAGE java AS '
Integer count = (Integer) state.get(type);  if (count == null) count = 1; else count++; state.put(type, count); return state; ' ;

CREATE OR REPLACE AGGREGATE group_and_count(text) 
SFUNC state_group_and_count 
STYPE map<text, int> 
INITCOND {};


CREATE FUNCTION state_group_and_total( state map<text, int>, type text, amount int )
CALLED ON NULL INPUT
RETURNS map<text, int>
LANGUAGE java AS '
Integer count = (Integer) state.get(type);  if (count == null) count = amount; else count = count + amount; state.put(type, count); return state; ' ;

CREATE FUNCTION state_group_and_float_total( state map<text, float>, type 
	, amount float )
CALLED ON NULL INPUT
RETURNS map<text, float>
LANGUAGE java AS '
Float count = (Float) state.get(type);  if (count == null) count = amount; else count = count + amount; state.put(type, count); return state; ' ;


CREATE OR REPLACE AGGREGATE group_and_total(text, int) 
SFUNC state_group_and_total 
STYPE map<text, int> 
INITCOND {};

CREATE OR REPLACE AGGREGATE group_and_float_total(text, float) 
SFUNC state_group_and_float_total 
STYPE map<text, float> 
INITCOND {};



------
CREATE OR REPLACE FUNCTION bookCountOnly(state map<text,bigint>)
RETURNS NULL ON NULL INPUT
RETURNS bigint
LANGUAGE java 
AS '
if(state.containsKey("Books")) {
 return (Long)state.get("Books");
} else {
 return 0L;
}';



CREATE OR REPLACE FUNCTION
cumulateCounterWithFilter(state map<text,bigint>, category text, count counter, filter text)
RETURNS NULL ON NULL INPUT
RETURNS map<text,bigint>
LANGUAGE java
AS '
if(category.equals(filter)) {
  if(state.containsKey(category)) {
    state.put(category, (Long)state.get(category) + count); 
  } else {
    state.put(category, count);
  }
}
return state;
'; 
 
CREATE OR REPLACE AGGREGATE groupCountHaving(text, counter, text)
SFUNC cumulateCounterWithFilter
STYPE map<text,bigint>
INITCOND {};
 
SELECT groupCountHaving(category,count, 'Books') 
FROM sales_items 
WHERE shop_id='BestDeals'
AND day>=20151221 AND day<=20151224; 


-----



CREATE TABLE customer_record_disposition (ssoid uuid, offername text, layout text, placement text, style text,dispositiontype text, recordtime timeuuid,
    primary key((ssoid,offername,dispositiontype),recordtime)
) WITH CLUSTERING ORDER BY (recordtime DESC) 
  AND compaction = {'class': 'TimeWindowCompactionStrategy', 
                    'compaction_window_size': 1, 
                    'compaction_window_unit': 'DAYS'};


INSERT INTO customer_record_disposition (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (uuid(),'loan_offer_01','l1','CustomerMessage','s1','viewed', now());

INSERT INTO customer_record_disposition (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (62f4bb6a-a92c-43e9-8d29-6c8d690ac7c3,'loan_offer_01','l1','CustomerMessage','s1','viewed', now());
INSERT INTO customer_record_disposition (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (62f4bb6a-a92c-43e9-8d29-6c8d690ac7c3,'loan_offer_02','l1','CustomerMessage','s1','viewed', now());

INSERT INTO customer_record_disposition (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (25c03ec4-c02a-4113-8c4e-de233aa2dcd2,'loan_offer_01','l1','CustomerMessage','s1','viewed', now());
INSERT INTO customer_record_disposition (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (25c03ec4-c02a-4113-8c4e-de233aa2dcd2,'loan_offer_02','l1','CustomerMessage','s1','viewed', now());

select toDate(recordtime) from customer_record_disposition where ssoid=62f4bb6a-a92c-43e9-8d29-6c8d690ac7c3 and offername='loan_offer_01' and dispositiontype='viewed';
select toTimestamp(recordtime) from customer_record_disposition where ssoid=62f4bb6a-a92c-43e9-8d29-6c8d690ac7c3 and offername='loan_offer_01' and dispositiontype='viewed';
select recordtime, toTimestamp(recordtime),min(recordtime) from customer_record_disposition where ssoid=62f4bb6a-a92c-43e9-8d29-6c8d690ac7c3 and offername='loan_offer_01' and dispositiontype='viewed';




CREATE TABLE customer_record_disposition2 (ssoid uuid, offername text, layout text, placement text, style text,dispositiontype text, recordtime timeuuid,
    primary key((ssoid),offername,dispositiontype,recordtime)
) WITH CLUSTERING ORDER BY (recordtime DESC) 
  AND compaction = {'class': 'TimeWindowCompactionStrategy', 
                    'compaction_window_size': 1, 
                    'compaction_window_unit': 'DAYS'};


INSERT INTO customer_record_disposition2 (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (uuid(),'loan_offer_01','l1','CustomerMessage','s1','viewed', now());
INSERT INTO customer_record_disposition2 (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (62f4bb6a-a92c-43e9-8d29-6c8d690ac7c3,'loan_offer_01','l1','CustomerMessage','s1','viewed', now());
INSERT INTO customer_record_disposition2 (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (62f4bb6a-a92c-43e9-8d29-6c8d690ac7c3,'loan_offer_02','l1','CustomerMessage','s1','viewed', now());
INSERT INTO customer_record_disposition2 (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (25c03ec4-c02a-4113-8c4e-de233aa2dcd2,'loan_offer_01','l1','CustomerMessage','s1','viewed', now());
INSERT INTO customer_record_disposition2 (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (25c03ec4-c02a-4113-8c4e-de233aa2dcd2,'loan_offer_02','l1','CustomerMessage','s1','viewed', now());

INSERT INTO customer_record_disposition2 (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (uuid(),'loan_offer_01','l1','CustomerMessage','s1','accepted', now());
INSERT INTO customer_record_disposition2 (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (62f4bb6a-a92c-43e9-8d29-6c8d690ac7c3,'loan_offer_01','l1','CustomerMessage','s1','accepted', now());
INSERT INTO customer_record_disposition2 (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (62f4bb6a-a92c-43e9-8d29-6c8d690ac7c3,'loan_offer_02','l1','CustomerMessage','s1','accepted', now());
INSERT INTO customer_record_disposition2 (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (25c03ec4-c02a-4113-8c4e-de233aa2dcd2,'loan_offer_01','l1','CustomerMessage','s1','accepted', now());
INSERT INTO customer_record_disposition2 (ssoid, offername, layout, placement,style,dispositiontype,recordtime) 
VALUES (25c03ec4-c02a-4113-8c4e-de233aa2dcd2,'loan_offer_02','l1','CustomerMessage','s1','accepted', now());



select ssoid, offername, dispositiontype, min(recordtime), toTimestamp(min(recordtime)) from customer_record_disposition2 where ssoid=62f4bb6a-a92c-43e9-8d29-6c8d690ac7c3 group by ssoid, offername, dispositiontype;
select ssoid, offername, dispositiontype, max(recordtime), toTimestamp(max(recordtime)) from customer_record_disposition2 where ssoid=62f4bb6a-a92c-43e9-8d29-6c8d690ac7c3 group by ssoid, offername, dispositiontype;



select ssoid, offername, dispositiontype, min(recordtime), toTimestamp(min(recordtime)) from customer_record_disposition2   group by ssoid, offername, dispositiontype;
select ssoid, offername, dispositiontype, max(recordtime), toTimestamp(max(recordtime)) from customer_record_disposition2   group by ssoid, offername, dispositiontype;


CREATE FUNCTION days_between_two_dates (a Date, b Date) RETURNS NULL ON NULL INPUT RETURNS int LANGUAGE java AS 'long diff = b.getTime() - a.getTime();
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);';

    CREATE TABLE ashu.customer_disposition (
    ssoid text,
    offername text,
    dispositiontype text,
    recordtime timeuuid,
    layout text,
    placement text,
    style text,
    PRIMARY KEY (ssoid, offername, dispositiontype, recordtime)
);


CREATE TABLE ashu.customer_disposition (
    ssoid text,
    offername text,
    dispositiontype text,
    recordtime timeuuid,
    layout text,
    placement text,
    style text,
    PRIMARY KEY (ssoid, offername, dispositiontype, recordtime)
) WITH CLUSTERING ORDER BY (offername ASC, dispositiontype ASC, recordtime ASC);
