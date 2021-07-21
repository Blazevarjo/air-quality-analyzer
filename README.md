# Air quality analyzer [![GitHub license](https://img.shields.io/github/license/RANDOM-V2N5q/air-quality-analyzer)](https://github.com/RANDOM-V2N5q/air-quality-analyzer/blob/master/LICENSE)


[Air quality analyzer](https://github.com/RANDOM-V2N5q/air-quality-analyzer) is an android app in which user can track data from air sensors from multiple location. The stations can be marked as tracked and then the data will be stored locally on the device. There are simple statistics shown to each of the sensors and interactive graph.

# Table of contents
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [Status](#status)
- [License](#license)

# Technologies

- Kotlin
- Room
- Retrofit
- WorkManager
- MPAndroidChart

[Here](app/build.gradle#L37) you can check all dependencies.

## Installation
### Prerequisites

Android studio is required to run the app locally.

### Setup

1. Download project directly from Github or pull it using git.
2. Open project with android studio.
3. Setup your emulator.
4. Run the app.

## Usage
- #### Main screen where user have list of currently tracked stations. From there user can: 
  - go to stations' deletion screen
  - go to station's sensors statistics screen
  - go to stations' addition screen
  - go to settings
<br/>
<img src="https://user-images.githubusercontent.com/46849151/125941619-66aad890-2f7d-41bd-95c8-fdae8f38aeda.png" width=500/>
<br/>

- #### Delete screen where user can choose multiple stations to delete them.

<br/>
<img src="https://user-images.githubusercontent.com/46849151/125941622-bb498e36-7c91-44da-9b96-9c812eb8d268.png" width=500/>
<br/>

- #### Station's sensors statistics screen where user can look at detailed informations about sensors and see the graph.


<br/>
<img src="https://user-images.githubusercontent.com/46849151/125941623-0c5af12d-686d-4ac3-aa83-34082b4cb2cd.png" width=500/>
<br/>

- #### After clicking on the graph, the user is given with fullscreen graph.

<br/>
<img src="https://user-images.githubusercontent.com/46849151/125941624-1337753f-7fa6-4292-b47b-73b99f12549b.png" width=800/>
<br/>

- #### On station's sensors statistics screen user can change sensor and timeline.

<br/>
<img src="https://user-images.githubusercontent.com/46849151/125941625-d99ada89-370d-4d65-9572-f74cc6a020e1.png" width=500/>
<br/>

- #### On stations' addition screen user can add various stations in order to track them.


<br/>
<img src="https://user-images.githubusercontent.com/46849151/125941627-b7d05b42-6a62-438c-8c9a-bd4f69af3683.png" width=500/>
<br/>

## Status

Currently, the app is considered as finished and there are no plans to do any updates on it.

## License

[MIT](LICENSE)
