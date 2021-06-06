import { css } from '../../../core/components/css-tag';

export const ExperienceCardStyle = css`
    .experience__card{
        background-color: white;
        border-radius: 8px;
        boxshadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2);
        padding: 10px;
        display: grid;
        grid-template-columns: 1fr 7fr;
        gap: 10px;
        align-items:center;
    }

    .experience__card__image {
        text-align: right;
    }
`;