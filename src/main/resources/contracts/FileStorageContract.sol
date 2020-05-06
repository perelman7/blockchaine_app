pragma solidity >= 0.4.0 < 0.7.0;

contract FileStorageContract {

    string filename;
    bytes content;
    string extension;
    string description;
    address sender;
    string recipient;
    int64 date;

    constructor(string memory _filename, bytes memory _content, string memory _extension, string memory _description, string memory _recipient, int64 _date) public{
        filename = _filename;
        content = _content;
        extension = _extension;
        description = _description;
        sender = msg.sender;
        recipient = _recipient;
        date = _date;
    }

    function getFilename() public view returns(string memory){
        return filename;
    }

    function getContent() public view returns (bytes memory){
        return content;
    }

    function getExtension() public view returns (string memory){
        return extension;
    }

    function getDescription() public view returns (string memory){
        return description;
    }

    function getSender() public view returns (address){
        return sender;
    }

    function getRecipient() public view returns (string memory){
        return recipient;
    }

    function getDate() public view returns (int64){
        return date;
    }
    
}