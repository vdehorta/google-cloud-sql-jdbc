POC pour l'utilisation de Google Cloud SQL.

1- Modifier la configuration de l'instance Google SQL avec votre propre instance, contenue dans la constante com.sfeir.cloudsqlpoc.service.impl.ArticleServiceJDBC.INSTANCE_PATH (le nom complet de l'instance peut se récupérer depuis votre interface d'administration Cloud SQL à l'adresse : https://code.google.com/apis/console).

2- Mettre dans le fichier de conf Appengine (appengine-web.xml), l'identifiant de votre application Appengine utilisant l'instance.

3- Le login et mot de passe de connexion ne sont pas spécifiés pour la connexion à la base car j'ai laissé ceux par défaut, respectivement "root" et "".