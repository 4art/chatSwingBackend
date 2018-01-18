Simplest way:
    macOS:
        Install software:
            brew install docker docker-machine docker-compose git httpie
        create docker-machine:
            docker-machine create group2
            docker-machine ls #look if it works correct
            eval $(docker-machine env groupB)
        run application:
            docker-compose up (or in background `docker-compose up -d`)
        check application:
            http $(docker-machine ip groupB)
            or
            curl $(docker-machine ip groupB)
        if you get {success: true} everything is works
    Ubuntu:
        https://www.digitalocean.com/community/tutorials/how-to-install-docker-compose-on-ubuntu-16-04

If you don't want something to install you can also use VPS with installed software for example Digital Ocean
    Web site: https://www.digitalocean.com/
    promo code for 10$: radio_t
    create droplet:
        Distributions: Ubuntu 16.04
        One-click apps: choose docker
    Choose a size: 1 GB 1 vCPU 25 GB 1 TB
    Choose a datacenter region: Frankfurt

    and create it
    after you go to the server with ssh you can clone the application with `git clone https://github.com/4art/chatSwingBackend.git`
    cd chatSwingBackend/
    docker-compose up -d

see you on the exam :)
