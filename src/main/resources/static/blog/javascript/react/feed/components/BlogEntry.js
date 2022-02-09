
class BlogEntry extends React.Component {


  



  render() {
    let likedByActiveUser = this.props.blogEntry.likedByActiveUser;


    let flaggedByActiveUser = this.props.blogEntry.flaggedByActiveUser;
 
  
    
    return (
    <div className = "list-item">
            <a className = "pic-anchor">
              <img className = "pic" src = {this.props.blogEntry.imageURL}></img>
            </a>
            <div className = "title">
              <h2 >{this.props.blogEntry.title}</h2>
              <p >{'by ' + this.props.blogEntry.authorUsername}</p>
            </div>
            <p className = "date">{this.props.blogEntry.timeStamp}</p>
            <p className = "description">{this.props.blogEntry.description}</p>
            <p className = "likes">
              Liked by
              <span className = "total-likes">
                {' ' + this.props.blogEntry.numberOfLikes + ' '}

              </span>
              users
            </p>

            <div className = "like-flag">
              <ThumbLogo liked = {likedByActiveUser}/>
              <FlagLogo flagged = {flaggedByActiveUser}/>
            </div>
            <WarningText flagged = {flaggedByActiveUser}/>
            
    </div>);

   
  }
}


