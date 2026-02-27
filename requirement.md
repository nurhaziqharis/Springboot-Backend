I want to create an saas application where every barber can manage queue of each client

Problem statement
1. Client must come to barbershop and queue and wait till their turn comes
2. Client do not have idea which barbershop is full. Need to come to shop first
3. Client need to wait the queue inside the shop. Its a time wasting
4. Client cannot know other people review and give feedback to barbershop
5. Barbershop need to manually manage their member royalty

Tech stack
1. Admin portal frontend - React + Vite Typescript + Tailwind
2. Mobile frontend (client)- Flutter
3. Mobile frontend (barbershop) - Flutter
3. Backend - Kotlin springboot

Feature
Client
1. Client can register
2. Client can login
3. In dashboard, client can see list of barbershop thatt subscribe the service
4. Client can select which barber they wish to proceed
4. Client can get the queue number in the app
5. Client can get notification each time the queue number increase
6. After finish hair cut, client can give rating /10

Barbershop
1. Barbershop can add their barber and manage it
2. Each barber can login into their account
3. Barbers can set the cut session finished and the queue number auto increase
4. If the client not coming to the barbershop, barber can skip the number
5. Barbershop can modify the barershop description and photos
6. Barbershop can see statistic of each barber like how many barber cut per day

Database design
// Use this code in dbdiagram.io

Table users {
  id integer [primary key]
  email varchar
  password_hash varchar
  name varchar
  role varchar // 'CLIENT', 'BARBER', 'SHOP_OWNER'
}

Table barbershops {
  id integer [primary key]
  owner_id integer [ref: > users.id]
  name varchar
  description text
  address varchar
  is_open boolean
  opening_time time
  closing_time time
}

Table barbers {
  id integer [primary key]
  user_id integer [ref: - users.id] // Link to auth credentials
  shop_id integer [ref: > barbershops.id]
  name varchar
  is_active boolean // Useful to toggle a barber's availability for the day
}

Table queues {
  id integer [primary key]
  shop_id integer [ref: > barbershops.id]
  client_id integer [ref: > users.id]
  barber_id integer [ref: > barbers.id, null] // Null until assigned
  queue_number varchar // Format: YYYYMMDD0001
  status varchar // 'WAITING', 'IN_PROGRESS', 'FINISHED', 'CANCELLED'
  joined_at timestamp
  started_at timestamp [null]
  finished_at timestamp [null]
}

Table ratings {
  id integer [primary key]
  queue_id integer [ref: - queues.id]
  score integer // 1-10
  comment text
}
