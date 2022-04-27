
export enum HttpMethod {
    GET = "GET", 
    POST = "POST", 
    PUT = "PUT", 
    DELETE = "DELETE"
}

export const placesDriveResource = {
    "links": {
        "create": {
            "href": "/places/default",
            "title": "New"
        },
        "nonVisited": {
            "href": "/non-visited-places",
            "title": "Places to Visit"
        }
    },
    "data": {
        "places": [
            {
                "links": {
                    "place": {
                        "href": "/places/1c0757e2-0502-45f4-a38d-e4dc44e2a090",
                        "title": "View"
                    },
                    "delete": {
                        "href": "/places/1c0757e2-0502-45f4-a38d-e4dc44e2a090",
                        "title": "Delete",
                        "method": "DELETE"
                    }
                },
                "data": {
                    "city": "Charlotte",
                    "state": "NC",
                    "country": "USA",
                    "visited": "2022-04-20"
                }
            }
        ]
    },
    "schema": {
        "type": "object",
        "id": "urn:jsonschema:io:pivotal:drive:view:PlaceSummaries",
        "readonly": true,
        "properties": {
            "places": {
                "type": "array",
                "items": {
                    "type": "object",
                    "id": "urn:jsonschema:io:pivotal:drive:view:PlaceSummary",
                    "properties": {
                        "city": {
                            "type": "string"
                        },
                        "state": {
                            "type": "string",
                            "enum": [
                                "TX",
                                "NC",
                                "MN"
                            ]
                        },
                        "country": {
                            "type": "string"
                        },
                        "visited": {
                            "type": "string",
                            "format": "date"
                        }
                    }
                }
            }
        }
    }
}

export const productsDriveResource = {
    links: {
        company: {
            href: "/company",
            title: "Company"
        }
    },
    data: {
        products: [
            {
                links: {
                    product: {
                        href: "/products/1",
                        title: "View"
                    }
                },
                data: {
                    name: "Ditch Digger",
                    category: "Tools",
                    price: "$0.99"
                }
            },
            {
                links: {
                    product: {
                        href: "/products/2",
                        title: "View"
                    }
                },
                data: {
                    name: "Dig Digger",
                    category: "Games",
                    price: "$1.99"
                }
            },
            {
                links: {
                    product: {
                        href: "/products/3",
                        title: "View"
                    },
                    delete: {
                        href: "/products/3",
                        title: "Delete",
                        method: HttpMethod.DELETE
                    }
                },
                data: {
                    name: "Claw Digger",
                    category: "Toys",
                    price: "$2.99"
                }
            }
        ]
    },
    schema: {
        title: "Products",
        type: "object",
        properties: {
            products: {
                type: "array",
                title: "Products",
                items: {
                    type: "object",
                    properties: {
                        name: {
                            type: "string",
                            title: "Name",
                            readonly: true
                        },
                        category: {
                            type: "string",
                            title: "Category",
                            readonly: true
                        },
                        price: {
                            type: "string",
                            title: "Price",
                            readonly: true
                        }
                    },
                    required: []
                },
            }
        },
        required: []
    }
}

export const companyDriveResource = {
    links: {
        products: {
            href: "/products",
            title: "Products"
        },
        edit: {
            href: "/company",
            title: "Update",
            type: "appliation/json",
            $ref: "#/definitions/resource",
            method: HttpMethod.PUT
        },
        delete: {
            href: "https://api.domain.com/resource",
            title: "Delete",
            method: HttpMethod.DELETE
        }
    },
    data: {
        company: "acmeinc",
        name: "acme",
        address: {
            city: "Davenport",
            state: "IA"
        },
        audit: {
            createdDate: "2015-12-25",
            updatedDate: "2020-12-25"
        }
    },
    schema: {
        title: "Company",
        type: "object",
        properties: {
            company: {
                title: "Company",
                type: "string",
                readOnly: false
            },
            name: {
                title: "Name",
                type: "string",
                pattern: "[a-z,A-Z]",
                readOnly: false
            },
            address: {
                type: "object",
                properties: {
                    city: {
                        type: "string",
                        title: "City",
                        readOnly: false,
                    },
                    state: {
                        type: "string",
                        title: "State",
                        enum: ["IA", "MN", "TX"],
                        readOnly: false,
                    },
                }
            },
            audit: {
                type: "object",
                properties: {
                    updatedDate: {
                        type: "string",
                        title: "Last Updated Date",
                        format: "date",
                        readOnly: true
                    },
                    createdDate: {
                        type: "string",
                        title: "Created Date",
                        format: "date",
                        readOnly: true
                    }
                }
            }
        },
        required: ["company", "name", "city", "state"]
    }
}

export const product1DriveResource = {
    links: {
        company: {
            href: "/company",
            title: "Company"
        },
        products: {
            href: "/products",
            title: "Products"
        }
    },
    data: {
        name: "Ditch Digger",
        category: "Tools",
        price: "$0.99",
        postedDate: "2021-12-25",
    },
    schema: {
        title: "Product",
        type: "object",
        properties: {
            name: {
                title: "Name",
                type: "string",
                readOnly: true
            },
            category: {
                title: "Category",
                type: "string",
                readOnly: true
            },
            price: {
                title: "Price",
                type: "string",
                readOnly: true
            },
            postedDate: {
                type: "string",
                title: "Created Date",
                format: "date",
                readOnly: true
            },
        },
        required: []
    }
}

export const product2DriveResource = {
    links: {
        company: {
            href: "/company",
            title: "Company"
        },
        products: {
            href: "/products",
            title: "Products"
        }
    },
    data: {
        name: "Dig Digger",
        category: "Games",
        price: "$1.99",
        postedDate: "2022-12-25",
    },
    schema: {
        title: "Product",
        type: "object",
        properties: {
            name: {
                title: "Name",
                type: "string",
                readOnly: true
            },
            category: {
                title: "Category",
                type: "string",
                readOnly: true
            },
            price: {
                title: "Price",
                type: "string",
                readOnly: true
            },
            postedDate: {
                type: "string",
                title: "Created Date",
                format: "date",
                readOnly: true
            },
        },
        required: []
    }
}

export const product3DriveResource = {
    links: {
        company: {
            href: "/company",
            title: "Company"
        },
        products: {
            href: "/products",
            title: "Products"
        }
    },
    data: {
        name: "Claw Digger",
        category: "Toys",
        price: "$3.99",
        postedDate: "2023-12-25",
    },
    schema: {
        title: "Product",
        type: "object",
        properties: {
            name: {
                title: "Name",
                type: "string",
                readOnly: true
            },
            category: {
                title: "Category",
                type: "string",
                readOnly: true
            },
            price: {
                title: "Price",
                type: "string",
                readOnly: true
            },
            postedDate: {
                type: "string",
                title: "Created Date",
                format: "date",
                readOnly: true
            },
        },
        required: []
    }
}