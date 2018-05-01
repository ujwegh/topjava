  getAll
  __________
  curl -X GET \
  http://localhost:8081/topjava/rest/profile/meals \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: f1926f44-3fab-45bc-afde-986e25f62400'
  
  get
  __________
  curl -X GET \
    http://localhost:8081/topjava/rest/profile/meals/100005 \
    -H 'Cache-Control: no-cache' \
    -H 'Postman-Token: 03a8adee-5ff7-44d2-b5f4-cc0df5b44284'
  
  create
  ___________
  curl -X POST \
    http://localhost:8081/topjava/rest/profile/meals \
    -H 'Cache-Control: no-cache' \
    -H 'Content-Type: application/json; charset=UTF8' \
    -H 'Postman-Token: 82119f24-0b66-4068-9a11-1f8e83a7710c' \
    -d '{
        "dateTime": "2015-06-21T14:20:00",
        "description": "Шаурма",
        "calories": 510
  }'
  
  update
  ___________
  curl -X PUT \
    http://localhost:8081/topjava/rest/profile/meals/100005 \
    -H 'Cache-Control: no-cache' \
    -H 'Content-Type: application/json; charset=UTF8' \
    -H 'Postman-Token: 67948caa-5318-4f70-92f6-2c0851ead7b3' \
    -d '{
        "dateTime": "2015-05-31T10:00:00",
        "description": "Шаурма",
        "calories": 510
  }'
  
  delete
  ___________
  curl -X DELETE \
    http://localhost:8081/topjava/rest/profile/meals/100005 \
    -H 'Cache-Control: no-cache' \
    -H 'Postman-Token: c1f56e29-19c5-4df6-b373-5bb41a0c16a5'
    
  getBetween
  ___________
  curl -X GET \
    'http://localhost:8081/topjava/rest/profile/meals/filter?startTime=10:00&endTime=15:00' \
    -H 'Cache-Control: no-cache' \
    -H 'Content-Type: application/json; charset=UTF-8' \
    -H 'Postman-Token: 7248ac2f-d15a-4f59-98e6-cb56ef3d57e2'