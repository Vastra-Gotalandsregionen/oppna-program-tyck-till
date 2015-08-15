# Innehåll #



# Introduktion #
Tyck Till är en applikation som används för att skicka felrapporter samt övrig feedback. Den används för att hantera felrapporter och övrig feedback i portalen. Felrapporterna skickas vidare till konfigurerbara mål(idag USD, Pivotal Tracker eller EMail). Ett portlet-filter samt ett servlet-filter finns för att fånga Java Exceptions som inte hanteras på annat sätt.

## Komponenter: ##
tyck-till-test-portlet (Visar hur en portlet anpassas för Tyck-till m.h.a. portletFilter)

tyck-till-test-servlet  (Visar hur en webapp anpassas för Tyck-till  m.h.a. servlet Filter)

tyck-till-core-bc-module-portlet(Tyck-till portlet med formulär i Iframe)

tyck-till-core-bc-module-web (Tyck-till applikationen)

tyck-till-core-bc-composite-svc ( Logik för routning av felrapporter till de olika målen)

## Beroenden (oppna-program): ##

commons-util-core-bc-composite-svc-ldap

commons-util-core-bc-composite-svc-usd

commons-util-core-bc-composite-svc-pivotaltracker

## Beroenden (tredje part): ##

Spring, Jersey, Axis, Orbeon, slf4j, aop-alliance, commons-codec, ... (se pom-filer för detaljer)

Applikationen bygger på ett formulär implementerat enligt standarden [XForms](http://www.w3.org/TR/xforms/). Som implementation av XForms används open source-produkten [Orbeon](http://www.orbeon.com). Se [referensarkitekturens anvisningar](http://code.google.com/p/oppna-program/wiki/Anvisningar_Anslutningsskikt_formularhantering_XForms) för mer information om hur XForms och Orbeon skall användas.

# Systemöversikt #

Nuvarande beta-version av Orbeon har inte gått att köra varken på WebSphere Portal 6.1 eller LifeRay 5.2.3. Därför kör vi Tyck till som en webapplikation på Tomcat tills vidare. Webbapplikationen är sedan inlänkad i portalen via en IFrame.

Test-portlet-modulen innehåller en enkelt testportlet som är till för att testa portletfiltret, som öppnar upp felformuläret i ett popupfönster. Filtret fångar både information om felet som uppstått samt om vilken användare som var inloggad.


## Orbeon-konfiguration ##

Tyck till kör _separat deployment_ av Orbeon enligt instruktionerna som återfinns [här](http://www.orbeon.com/ops/doc/reference-xforms-java). Orbeon.war har döpts om till orbeon-3.7.0.war och deployats på Tomcat - detta för att man ska kunna köra flera olika versioner samtidigt.

Observera att filterklassen är felaktigt namngiven i Orbeon-dokumentationen ovan. För orbeon-3.7.1 ska det vara (se web.xml i core-bc/modules/web modulen):
```
<filter-name>orbeon-xforms-filter</filter-name>
  	<filter-class>org.orbeon.oxf.servlet.OPSXFormsFilter</filter-class>
  	<init-param>
  		<param-name>oxf.xforms.renderer.context</param-name>
  		<param-value>/orbeon</param-value>
  	</init-param>
</filter>
```

Cross-context-setup (enligt Orbeon-dokumentationen ovan) har satts genom en context.xml fil under META-INF-katalogen i core-bc/modules/web modulen.

# Sätta upp sin lokala utvecklingsmiljö #

För att utveckla och köra projektet lokalt används referensarkitekturens utvecklingsmiljö. Börja därför med att sätta upp utvecklingsmiljön enligt [referensarkitekturens instruktioner](http://code.google.com/p/oppna-program/wiki/Anvisningar_Utvecklingsmiljo). Se [Source](http://code.google.com/p/oppna-program-tyck-till/source/checkout) för information om hur du checkar ut källkoden.

## Installation av Orbeon ##

Ladda hem Orbeon 3.7.1 från http://forge.ow2.org/project/showfiles.php?group_id=168.

Packa upp distributionen till en temporär katalog. Gå till Eclipse. Välj import->war-file. Peka ut orbeon.war som ligger i roten på orbeon-distributionen. Använd defaultinställningar.

Välj refactor->rename på projektet. Döp om det till "orbeon-3.7.0".

Deploya orbeon-3.7.0 på Tomcat via Eclipse (genom att välja _Add and remove projects_ på servern).

## Deploy av web-projektet ##

Deploya även /tyck-till-core-bc-module-web-projektet i Tomcat från Eclipse. Formuläret deployas default på `http://<Tomcat URL>/tyck-till-core-bc-module-web/xforms-jsp/index.jsp`.


## Testa portlets lokalt ##

Tyck till innehåller två portlets. Maven-modulen core-bc/module/portlet innehåller produktionsportletten, som länkar in tyck-till-formuläret i en IFrame. Test-portlet-modulen innehåller en enkelt testportlet som är till för att kasta exceptions och testa Portletfiltret, som i sin tur öppnar upp felformuläret i ett popupfönster. Båda portlettarna har inställningar i portlet.xml . Dessa inställningar behöver ändras beroende på vilken miljö man vill köra mot.
```

```


För att testa/utveckla dessa lokalt har OpenPortlet Container på Glassfish använts. Se [referensarkitekturens anvisningar för portlets](http://oppna-program.googlecode.com/svn/reference-architecture/trunk/docs/Teknisk_arkitektur/Systemutveckling/JavaEE/Anvisningar_Anslutningsskikt_Portlet.doc) för mer information om utveckling av JSR 286-portlets och hur installationen av OpenPortlet Container och Glassfish går till.

### Deploy av portlets till OpenPortlet Container ###

Starta Glassfish. Gå därefter till http://localhost:8080/portletdriver/admin. Välj tyck-till-portlet.war och tryck på Deploy. Gå därefter till Portlets-fliken där default-portletfönstret automatiskt har uppdaterats med tyck-till-portleten.

Gör på samma vis för att deploya tyck-till-test-portlet.war.

# Att skapa en release på google code #

Instruktioner finns [här](http://code.google.com/p/oppna-program/wiki/Bygganvisningar_Oppna_Program).