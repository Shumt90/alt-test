HOST=$1

echo $HOST

curl "$HOST/api/8news/tags/page?tagType=player&tagUrl=aleksandr-karavayev&tab=calendar&newsLimit=30&materialsLimit=5"
curl "$HOST/api/8news/tags/page?tagType=player&tagUrl=yevgeniy-chernov&tab=review&newsLimit=30&materialsLimit=5"
curl "$HOST/api/8news/tags/page?tagType=player&tagUrl=stipe-peritsa&tab=calendar&newsLimit=30&materialsLimit=5"
curl "$HOST/api/8news/tags/page?tagType=player&tagUrl=shaun-makdonald&tab=calendar&newsLimit=30&materialsLimit=5"
curl "$HOST/api/8news/tags/page?tagType=player&tagUrl=lis-musset&tab=calendar&newsLimit=30&materialsLimit=5"
curl "$HOST/api/8news/tags/page?tagType=player&tagUrl=diyego-reyyes&tab=materials&newsLimit=30&materialsLimit=10"
curl "$HOST/api/8news/tags/page?tagType=player&tagUrl=aleksey-shevchenko&tab=calendar&newsLimit=30&materialsLimit=5"