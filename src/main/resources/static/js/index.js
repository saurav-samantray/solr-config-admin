var app = new Vue({
    el: '#app',
    data: {
        isZkConnected: false,
        isSolrConnected: false,
        zkHostString: 'localhost:9983',
        errorMessage: null,
        configSets: [],
        collections: [],
        spinner: true,
        configSetPath: 'D:/utilities/solr-7.5.0/sephora_poc',
        configSetName: 'dummy',
        collectionDetails: null,

    },
    watch: {},
    mounted() {
        axios
            .get("api/solr/connection")
            .then(response => {
                this.spinner = false
                console.log(response)
                this.isZkConnected = response.data.zkConnected
                this.isSolrConnected = response.data.solrConnected
                this.zkHostString = response.data.zkHostString
                this.configSets = response.data.configSets
                this.collections = response.data.collections
                this.spinner = false
            }).catch(error => {
                this.spinner = false
                console.log("Exception caught from server: " + error)
                this.errorMessage = JSON.stringify(error.response.statusText)
                setTimeout(() => {
                    this.errorMessage = null;
                }, 5000);

            });
    },
    methods: {
        connect: function() {
            this.spinner = true
            axios
                .post("api/solr/initialize?zkHostString=" + this.zkHostString)
                .then(response => {
                    console.log(response)
                    this.spinner = false
                    this.isZkConnected = response.data.zkConnected
                    this.isSolrConnected = response.data.solrConnected
                    this.zkHostString = response.data.zkHostString
                    this.configSets = response.data.configSets
                    this.collections = response.data.collections
                }).catch(error => {
                    this.spinner = false
                    console.log("Exception caught from server: " + error)
                    this.errorMessage = JSON.stringify(error.response.statusText)
                    setTimeout(() => {
                        this.errorMessage = null;
                    }, 5000);

                })
        },
        disconnect: function() {
            this.spinner = true
            axios
                .delete("api/solr/connection")
                .then(response => {
                    console.log(response)
                    this.spinner = false
                    this.isZkConnected = response.data.zkConnected
                    this.isSolrConnected = response.data.solrConnected
                    this.zkHostString = response.data.zkHostString
                    this.configSets = response.data.configSets
                    this.collections = response.data.collections
                }).catch(error => {
                    this.spinner = false
                    console.log("Exception caught from server: " + error)
                    this.errorMessage = JSON.stringify(error.response.statusText)
                    setTimeout(() => {
                        this.errorMessage = null;
                    }, 5000);

                })
        },
        deleteConfig: function(configSetName) {
            this.spinner = true
            axios
                .delete("api/solr/configSet?configSetName=" + configSetName)
                .then(response => {
                    console.log(response)
                    this.spinner = false
                    this.configSets = response.data
                }).catch(error => {
                    this.spinner = false
                    console.log("Exception caught from server: " + error)
                    this.errorMessage = JSON.stringify(error.response.statusText)
                    setTimeout(() => {
                        this.errorMessage = null;
                    }, 5000);

                })
        },
        uploadConfig: function(configSetName) {
            this.spinner = true
            axios
                .post("api/solr/configSet?configSetPath=" + this.configSetPath + "&configSetName=" + this.configSetName)
                .then(response => {
                    console.log(response)
                    this.spinner = false
                    this.configSets = response.data.configSets
                }).catch(error => {
                    this.spinner = false
                    console.log("Exception caught from server: " + error)
                    this.errorMessage = JSON.stringify(error.response.statusText)
                    setTimeout(() => {
                        this.errorMessage = null;
                    }, 5000);

                })
        },
        editConfig: function(configSetName) {
            this.configSetName = configSetName
            //this.configSetPath = ''
        },
        getCollectionDetails: function(collection) {
            axios
                .get("api/solr/collections/" + collection)
                .then(response => {
                    this.spinner = false
                    console.log(response)
                    this.collectionDetails = response.data

                }).catch(error => {
                    this.spinner = false
                    console.log("Exception caught from server: " + error)
                    //this.errorMessage = JSON.stringify(error.response.statusText)
                    setTimeout(() => {
                        this.errorMessage = null;
                    }, 5000);

                });
        }


    }
});