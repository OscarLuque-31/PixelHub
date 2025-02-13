package models;

import java.util.List;

public class Game {
    public int id;
    public String name;
    public String released;
    public String background_image;
    public String description;
    public double rating;
    public List<ParentPlatform> parent_platforms;
    public List<Genre> genres;
	public List<Screenshot> shortScreenshots;

    public static class ParentPlatform {
        public PlatformDetail platform;

        public static class PlatformDetail {
            public String name;
            public String slug;
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
            
        }

		public PlatformDetail getPlatform() {
			return platform;
		}

		public void setPlatform(PlatformDetail platform) {
			this.platform = platform;
		}
    }

    public static class Genre {
        public int id;
        public String name;
        public String slug;
    }

    public static class Screenshot {
        public String image;

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}
        
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ParentPlatform> getParentPlatforms() {
		return parent_platforms;
	}

	public void setParentPlatforms(List<ParentPlatform> parentPlatforms) {
		this.parent_platforms = parentPlatforms;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getBackgroundImage() {
		return background_image;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.background_image = backgroundImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", name=" + name + ", released=" + released + ", background_image=" + background_image
				+ ", description=" + description + ", rating=" + rating + ", parent_platforms=" + parent_platforms
				+ ", genres=" + genres + ", shortScreenshots=" + shortScreenshots + "]";
	}
	
	
    
}
