class BaseModel {
    constructor(id) {
        this._id = id || null; 
    }

    
    getId() {
        return this._id;
    }

    
    setId(id) {
        this._id = id;
    }
}

module.exports = BaseModel;
